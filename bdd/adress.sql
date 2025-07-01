-- la cration des tables pour l'addressse 
create table pays
(
pay_id int identity primary key,
nom_pay nvarchar(50) not null
);
create table wilaya
(
wilaya_id int identity primary key,
nom_wilaya nvarchar(50) not null,
code_wilaya varchar(5) not null,
pay int references pays(pay_id) not null
);
create table daira 
(
daira_id int identity primary key,
nom_daira nvarchar(50) not null,
wilaya int references wilaya(wilaya_id) not null
);
create table commune
(
commune_id int identity primary key,
nom_commune nvarchar(50) not null,
code_commune varchar(3) not null,
daira int references daira(daira_id) not null,
);
create table Rue
(
rue_id int identity primary key,
nom_Rue nvarchar(100),
commune int references commune(commune_id)
);
create table addresse
(
addresse_id int identity primary key,
designation nvarchar(100) unique not null,
Rue int references Rue(rue_id)
);

-------------------- procedures -----------------
--             pay
go 
create procedure getPayID
	@nom nvarchar(50),
	@id int output
as
begin
	select @id = pay_id 
	from pays 
	where nom_pay=@nom
end

go
create procedure addPay
@nom nvarchar(50),
@id int output
as 
begin
	exec getPayID @nom,@id output
	if(@id is null)
	begin
		insert into pays(nom_pay) 
		VALUES (@nom)
		exec getPayID @nom,@id output 
		-- pour recuperer l'id du pay ajouté
	end
end
--            wilaya
go 
create procedure getwilayaID
	@nom nvarchar(50),
	@code varchar(5),
	@pay int,
	@id int output
as
begin
	select @id = wilaya_id 
	from wilaya 
	where nom_wilaya=@nom 
	and code_wilaya = @code 
	and pay = @pay
end

go
create procedure addwilaya
@nom nvarchar(50),
@code varchar(5),
@pay int,
@id int output

as
begin
	exec getwilayaID @nom,@code,@pay,@id output
	if(@id is null)
	begin
		insert into wilaya(nom_wilaya, code_wilaya, pay) 
		values (@nom, @code, @pay)
		exec getwilayaID @nom,@code,@pay,@id output
	end
end
--           daira
go
create procedure getdairaID
@nom nvarchar(50),
@wilaya int,
@id int output
as
begin
	select @id = daira_id 
	from daira 
	where nom_daira = @nom 
	and wilaya = @wilaya;
end

go
create procedure adddaira
@nom nvarchar(50),
@wilaya int,
@id int output
as
begin
	exec getdairaID @nom,@wilaya,@id output
	if(@id is null)
	begin
		insert into daira(nom_daira,wilaya) 
		values (@nom,@wilaya)
		exec getdairaID @nom,@wilaya,@id output
	end
end
--            commune
go
create procedure getcommuneID
@nom nvarchar(50),
@code varchar(3),
@daira int,
@id int output
as
begin
	select @id = commune_id 
	from commune 
	where nom_commune = @nom 
	and code_commune = @code 
	and daira = @daira;
end

go
create procedure addcommune
@nom nvarchar(50),
@code varchar(3),
@daira int,
@id int output
as
begin
	exec getcommuneID @nom,@code,@daira,@id output
	if (@id is null)
	begin
		insert into commune(nom_commune,code_commune,daira) 
		values (@nom,@code,@daira)
		exec getcommuneID @nom,@code,@daira,@id output
	end
end
--        Rue
go
create procedure getRueID
@nom nvarchar(50),
@commune int,
@id int output
as
begin
	select @id = rue_id 
	from Rue 
	where nom_Rue = @nom 
	and commune = @commune;
end

go
create procedure addRue
@nom nvarchar(50),
@commune int,
@id int output
as
begin
	exec getRueID @nom,@commune,@id output
	if(@id is null)
	begin
		insert into Rue(nom_Rue,commune) 
		values (@nom,@commune)
		exec getRueID @nom,@commune,@id output
	end
end
--       addresse
go 
create procedure getAddresse
@nompay nvarchar(50),
@nomwilaya nvarchar(50),
@codewilaya varchar(5),
@nomdaira nvarchar(50),
@nomcommune nvarchar(50),
@codecommune varchar(3),
@nomRue nvarchar(50),
@designation nvarchar(50),
@id int output
as
begin
    SET NOCOUNT ON; -- supprime ces message (1 row(s) affected)

    SELECT @id = a.addresse_id
FROM addresse a
JOIN Rue r ON a.Rue = r.rue_id
JOIN commune c ON r.commune= c.commune_id
JOIN daira d ON c.daira = d.daira_id
JOIN wilaya w ON d.wilaya = w.wilaya_id
JOIN pays p ON w.pay = p.pay_id
WHERE a.designation = @designation
  AND r.nom_Rue = @nomRue
  AND c.code_commune = @codecommune
  AND c.nom_commune = @nomcommune
  AND d.nom_daira = @nomdaira
  AND w.code_wilaya = @codewilaya
  AND w.nom_wilaya = @nomwilaya
  AND p.nom_pay = @nompay
	
end


go
create procedure getaddresseID
@nom nvarchar(50),
@Rue int,
@id int output
as
begin
	select @id = addresse_id 
	from addresse 
	where designation = @nom 
	and Rue = @Rue;
end

go
create procedure addaddresse
@nompay nvarchar(50),
@nomwilaya nvarchar(50),
@codewilaya varchar(5),
@nomdaira nvarchar(50),
@nomcommune nvarchar(50),
@codecommune varchar(3),
@nomRue nvarchar(50),
@designation nvarchar(50)
as
begin
	declare @payid as int
	declare @wilayaid as int
	declare @dairaid as int 
	declare @communeid as int
	declare @Rueid as int
	declare @addresseid as int

	-- ajouter le pay :
	exec addPay @nompay,@id = @payid output
	
	-- ajouter la wilaya :
	exec addwilaya @nomwilaya,@codewilaya,@payid,@id = @wilayaid output
	
	-- ajouter la daira :
	exec adddaira @nomdaira,@wilayaid,@id = @dairaid output
	
	-- ajouter la commune :
	exec addcommune @nomcommune,@codecommune,@dairaid,@id = @communeid output

	-- ajouter la Rue :
	exec addRue @nomRue,@communeid,@id = @Rueid output
	
	-- ajouter addresse :
	insert into addresse(designation,Rue) values (@designation,@Rueid)
	
end

--                     afficher une addresse
go
create procedure showaddresse
@id int
as
begin

	SELECT 
		   p.nom_pay, 
		   w.nom_wilaya,
		   w.code_wilaya, 
		   d.nom_daira, 
		   c.nom_commune, 
		   c.code_commune, 
		   r.nom_Rue, 
		   a.designation 
	   FROM addresse a 
	   JOIN Rue r ON a.Rue = r.rue_id 
	   JOIN commune c ON r.commune = c.commune_id 
	   JOIN daira d ON c.daira = d.daira_id 
	   JOIN wilaya w ON d.wilaya = w.wilaya_id
	   JOIN pays p ON w.pay = p.pay_id
	   WHERE a.addresse_id = @id
end
--                   show all addresse 
go
create procedure showalladdresse
as 
begin
	SELECT 
		   p.nom_pay, 
		   w.nom_wilaya,
		   w.code_wilaya, 
		   d.nom_daira, 
		   c.nom_commune, 
		   c.code_commune, 
		   r.nom_Rue, 
		   a.designation 
	   FROM addresse a 
	   JOIN Rue r ON a.Rue = r.rue_id 
	   JOIN commune c ON r.commune = c.commune_id 
	   JOIN daira d ON c.daira = d.daira_id 
	   JOIN wilaya w ON d.wilaya = w.wilaya_id
	   JOIN pays p ON w.pay = p.pay_id
end
--                   supprimer une addresse
go 
create procedure supaddresse
@id int
as
begin
	DECLARE @Rue_id int , 
			@commune_id int , 
			@daira_id int , 
			@wilaya_id int , 
			@pay_id int 

    -- Récupérer toutes les id liées à l'adresse
    SELECT 
        @rue_id = a.Rue,
        @commune_id = r.commune,
        @daira_id = c.daira,
        @wilaya_id = d.wilaya,
        @pay_id = w.pay
    FROM addresse a
    JOIN Rue r ON a.Rue = r.rue_id
    JOIN commune c ON r.commune = c.commune_id
    JOIN daira d ON c.daira = d.daira_id
    JOIN wilaya w ON d.wilaya = w.wilaya_id
    WHERE a.addresse_id = @id;

    -- Supprimer l'adresse
    DELETE FROM addresse WHERE addresse_id = @id;

    -- Supprimer la rue si elle n'est plus utilisée
    IF NOT EXISTS (SELECT 1 FROM addresse WHERE Rue = @rue_id)
    BEGIN
        DELETE FROM Rue WHERE rue_id = @rue_id;
    END

    
    IF NOT EXISTS (SELECT 1 FROM Rue WHERE commune = @commune_id)
    BEGIN
        DELETE FROM commune WHERE commune_id = @commune_id;
    END


    IF NOT EXISTS (SELECT 1 FROM commune WHERE daira = @daira_id)
    BEGIN
        DELETE FROM daira WHERE daira_id = @daira_id;
    END

    
    IF NOT EXISTS (SELECT 1 FROM daira WHERE wilaya = @wilaya_id)
    BEGIN
        DELETE FROM wilaya WHERE wilaya_id = @wilaya_id;
    END

    
    IF NOT EXISTS (SELECT 1 FROM wilaya WHERE pay = @pay_id)
    BEGIN
        DELETE FROM pays WHERE pay_id = @pay_id;
    END
end
--                           modifier une adresse

--  l'utilisateur qui click sur un text field ynjm ymodifi le champ li brah
--  bon deja fl front ki user click sur modifier les champ n3amrhom
--  b la procedure showaddresse w ki tmodifi un champ n3ayt
--  lhad procedure tdir khdmtha 
go 

create procedure modifieraddresse
@id int,
@nompay nvarchar(50),
@nomwilaya nvarchar(50),
@codewilaya varchar(5),
@nomdaira nvarchar(50),
@nomcommune nvarchar(50),
@codecommune varchar(3),
@nomRue nvarchar(50),
@designation nvarchar(50)
as
begin
	DECLARE @Rue_id int , 
			@commune_id int , 
			@daira_id int , 
			@wilaya_id int , 
			@pay_id int
	SELECT 
        @rue_id = a.Rue,
        @commune_id = r.commune,
        @daira_id = c.daira,
        @wilaya_id = d.wilaya,
        @pay_id = w.pay
    FROM addresse a
    JOIN Rue r ON a.Rue = r.rue_id
    JOIN commune c ON r.commune = c.commune_id
    JOIN daira d ON c.daira = d.daira_id
    JOIN wilaya w ON d.wilaya = w.wilaya_id
    WHERE a.addresse_id = @id;
	-- Vérifier si l'adresse existe bach ila user click f une column khawya
	-- ma thsich 9adra tsra
    IF EXISTS (SELECT 1 FROM addresse 
				WHERE addresse_id = @id)
	begin
		--modifier le pay :
		update pays 
		set nom_pay = @nompay
		where pay_id = @pay_id

		--modifier la wilaya :
		update wilaya 
		set nom_wilaya = @nomwilaya,
			code_wilaya = @codewilaya
		where wilaya_id = @wilaya_id
		and pay = @pay_id

		--modifier la daira :
		update daira
		set nom_daira = @nomdaira
		where daira_id = @daira_id
		and wilaya = @wilaya_id

		--modifier la commune :
		update commune
		set nom_commune = @nomcommune,
			code_commune = @codecommune
		where commune_id = @commune_id
		and daira = @daira_id

		--modifier la Rue :
		update Rue
		set nom_Rue = @nomRue
		where rue_id = @Rue_id
		and commune = @commune_id

		--modifier addresse :
		update addresse
		set designation = @designation
		where addresse_id = @id
		and Rue = @Rue_id
			
	end
end
                 ----------- test -----------
exec addaddresse algerie , oran , 31 , 'ain turk' , 'mers elkebir' , 111 , 'hai dadayoum' , imed;
exec showaddresse 1012;
exec supaddresse 10;
exec showalladdresse;
exec modifieraddresse 11 , algerie , oran , 31 , tirigo , tirigo , 222 , 'en face ecole' , binome;

DECLARE @id INT;

EXEC getAddresse
    @nompay = 'algerie',
    @nomwilaya = 'oran',
    @codewilaya = '31',
    @nomdaira = 'tirigo',
    @nomcommune = 'tirigo',
    @codecommune = '222',
    @nomRue = 'en face ecole',
    @designation = 'binome',
    @id = @id OUTPUT;

-- Affiche la valeur retournée
SELECT @id AS id_retourne;
