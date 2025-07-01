-- procédures stockées 
--                                 personne

--                    procedure qui retourne l'id d'une personne 
go
create procedure getpersonne
    @nom nvarchar(50),
    @prenom nvarchar(50),
    @email nvarchar(100),
    @phone varchar(30),
    @id int output
as
begin
	set nocount on;
    select @id = personne_id
    from personne
    where nom = @nom
      and prenom = @prenom
      and email = @email
      and phone = @phone
end

--                     procedure qui ajoute une personne
go
create procedure addpersonne
    @nom nvarchar(50),
    @prenom nvarchar(50),
    @email nvarchar(100),
    @phone varchar(30),
    @id int output
as
begin
	set nocount on;
    exec getpersonne @nom, @prenom, @email, @phone, @id output
    if (@id is null)
    begin
        insert into personne(nom, prenom, email, phone)
        values (@nom, @prenom, @email, @phone)

        exec getpersonne @nom, @prenom, @email, @phone, @id output
		-- recuperer l'id de la personne
    end
end
-----------------------------------------------------------------------------------------
--                             client

--                         procedure qui ajoute un client

go
create procedure addclient
    @nom nvarchar(50),
    @prenom nvarchar(50),
    @email nvarchar(100),
    @phone varchar(30),
    @type_client nvarchar(15),
    @date_inscription date,
    @details_client nvarchar(500) = null
as
begin
	set nocount on;
    declare @id int
    exec addpersonne @nom, @prenom, @email, @phone, @id output
    insert into client(client_id, type_client, date_inscription, details_client)
    values (@id, @type_client, @date_inscription, @details_client)
end

--                    procedure pour modifier les infos des clients

go
create procedure modclient
    @id int,
    @nom nvarchar(50),
    @prenom nvarchar(50),
    @email nvarchar(100),
    @phone varchar(30),
    @type_client nvarchar(15),
    @date_inscription date,
    @details_client nvarchar(500) = null
as
begin
	set nocount on;
    if exists (select 1 from client where client_id = @id)
    begin
        update personne
        set nom = @nom,
            prenom = @prenom,
            email = @email,
            phone = @phone
        where personne_id = @id

        update client
        set type_client = @type_client,
            date_inscription = @date_inscription,
            details_client = @details_client
        where client_id = @id
    end
end

--                           procedure pour supprimer un client 

go
create procedure supclient
    @id int
as
begin
	set nocount on;
    delete from client where client_id = @id;
    delete from personne where personne_id = @id;
end

--                    procedure qui affiche un client selon les informations fournis
go
create procedure rechercher_client
     @critere nvarchar(100) = null
as
begin
    set nocount on;
    select p.*, c.*
    from personne p
    join client c on p.personne_id = c.client_id
    where 
		@critere is null
        or p.nom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.prenom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.email collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.phone collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
end

--  procedure pour afficher tout les detaille du client donne ou de tout les client si il n'ya pas de parametre 

go
create procedure showclientdetails
    @id int = null
as
begin
	set nocount on;
    select p.*, c.*
    from personne p
    join client c on p.personne_id = c.client_id
    where @id is null or p.personne_id = @id;
end
--------------------------------------------------------------------------------------------------------------											  
--                                  employe				

--                        procedure pour creer un employe
go
create procedure addemploye
    @matricule nvarchar(5),
	@nom nvarchar(50),
    @prenom nvarchar(50),
    @email nvarchar(100),
    @phone varchar(30),   
    @statut nvarchar(8),    
    @numss nvarchar(20),
	@heures_travaille decimal(4,2),
	@addresse nvarchar(50),
    @lieu_naissance nvarchar(50),
    @date_naissance date,
    @date_recru date,
	@details nvarchar(500) = null
as
begin
	set nocount on;
    declare @id int;
    exec addpersonne @nom, @prenom, @email, @phone, @id output
    insert into employe(employe_id,statut, matricule, num_securite_sociale,heures_travail_par_jour,addresse,lieu_naissance,date_naissance, date_recrutement, details)
    values (@id, @statut, @matricule, @numss,@heures_travaille,@addresse,@lieu_naissance,@date_naissance, @date_recru, @details);
end

--                             procedure pour modifier un employe	
go
create procedure modemploye
    @id int,
	@nom nvarchar(50),
    @prenom nvarchar(50),
    @email nvarchar(100),
    @phone varchar(30),   
    @statut nvarchar(8),    
    @numss nvarchar(20),
	@heures_travaille decimal(4,2),
	@addresse nvarchar(50),
    @lieu_naissance nvarchar(50),
    @date_naissance date,
    @date_recru date,
	@details nvarchar(500) = null
as
begin
	set nocount on;
    if exists (select 1 from employe where employe_id = @id)
    begin
        update personne
        set nom = @nom,
            prenom = @prenom,
            email = @email,
            phone = @phone           
        where personne_id = @id;

        update employe
        set statut = @statut,           
            num_securite_sociale = @numss,
			heures_travail_par_jour = @heures_travaille,
            date_recrutement = @date_recru,
			addresse = @addresse,
			lieu_naissance = @lieu_naissance,
            date_naissance = @date_naissance,
			details = @details
        where employe_id = @id;
    end
end
--						 rendre l'employe inactif			 
go
create procedure desactiver_employe
    @id int
as
begin
                                  
	set nocount on;
	declare @statut nvarchar(8);

	select @statut = statut from employe where employe_id = @id; 
	if @statut = 'actif'
	begin
		update employe
		set statut = 'inactif'
		where employe_id = @id;

		delete from horaire_employe 
		where employe_id = @id;
	end
	else if @statut = 'inactif'
	begin
		update employe
		set statut = 'actif'
		where employe_id = @id;
	end
    
end

--                                procedure pour chercher les employe
go
create procedure rechercher_employe
    @critere nvarchar(100) = null
as
begin
	set nocount on;
    select p.*, e.*
    from personne p
    join employe e on p.personne_id = e.employe_id
    where 
		@critere is null
        or p.nom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.prenom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.email collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.phone collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or e.matricule collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
end

--                  procedure pour afficher tout les detailles des employées
go
create procedure showemployedetails
    @id int = null
as
begin
	set nocount on;
    select p.*, e.*
    from personne p
    join employe e on p.personne_id = e.employe_id
    where @id is null or p.personne_id = @id;
end
--------------------------------------------------------------------------------------------------
--                            poste
--                       procedure pour ajouter un poste
go
create procedure addposte
    @titre nvarchar(50),
    @salaire_base decimal(8,2),
    @description_poste nvarchar(500) = null
as
begin
	set nocount on;
    insert into poste(titre, salaire_base, description_poste)
    values (@titre, @salaire_base, @description_poste);
end
--                           procedure pour modifier un poste           
go
create procedure modposte
    @poste_id int,
    @titre nvarchar(50),
    @salaire_base decimal(8,2),
    @description_poste nvarchar(500) = null
as
begin
	set nocount on;
    if exists (select 1 from poste where poste_id = @poste_id)
    begin
        update poste
        set titre = @titre,
            salaire_base = @salaire_base,
            description_poste = @description_poste
        where poste_id = @poste_id;
    end
end
--               procedure pour supprimer un post
go 
create procedure suppost
	@id int
as
begin
	set nocount on;
    delete from poste where poste_id = @id;
end
--                     procedure qui retourne id d'un poste donne
go
create procedure getposte
	@titre nvarchar(50),
    @id int output
as
begin
	set nocount on;
	if exists (select 1 from poste where titre = @titre)
	begin
		select @id = poste_id from poste where @titre = titre;
	end
	else
		begin
			raiserror('ce Poste n existe pas !', 16, 1);
		end
end
--                 procedure qui affiche tout les postes
go
create procedure showallposte
as
begin
	set nocount on;
    select * from poste;
end
-------------------------------------------------------------------------------------------------------------
--                                   affectation

--              procedure qui ajoute une affectation
go
create procedure addaffectation
    @employe_id int,
    @poste_id int,
    @date_debut date,
    @date_fin date null,
    @salaire_negocie decimal(10,2)
as
begin
	set nocount on;
    if exists (select 1 from employe where employe_id = @employe_id)
       and exists (select 1 from poste where poste_id = @poste_id)
    begin
        insert into affectation(employe, poste, date_debut, date_fin, salaire_negocie)
        values (@employe_id, @poste_id, @date_debut, @date_fin, @salaire_negocie);
    end
end
--                 procedure pour modifier une affectation
go
create procedure modaffectation
    @affectation_id int,
    @employe_id int,
    @poste_id int,
    @date_debut date,
    @date_fin date,
    @salaire_negocie decimal(10,2)
as
begin
	set nocount on;
    if exists (select 1 from affectation where affectation_id = @affectation_id)
    begin
        update affectation
        set employe = @employe_id,
            poste = @poste_id,
            date_debut = @date_debut,
            date_fin = @date_fin,
            salaire_negocie = @salaire_negocie
        where affectation_id = @affectation_id;
    end
end
--                        procedure pour afficher les affectations des employes
go
create procedure showaffectation 
    @employe_id int
as
begin
	set nocount on;
    select  po.titre as poste,a.date_debut, a.date_fin, a.salaire_negocie          
    from affectation a
    join poste po on a.poste = po.poste_id
    where a.employe = @employe_id;
end

go
create procedure getaffectation
	@employe_id int,
    @poste_id int,
	@id int output
as
begin
	set nocount on;
	select @id = affectation_id
	from affectation
	where employe = @employe_id
	and poste = @poste_id

end
--------------------------------------------------------------------------------------------------------------
--                       prime
--                  procedure qui ajoute une prime
go
create procedure addprime
    @employe_id int,
    @prime decimal(10,2),
    @type_prime nvarchar(50) null,
    @date_attribution date,
    @date_fin date null,
    @commentaire nvarchar(500) null
as
begin
	set nocount on;
	declare @affectation_id int
	select @affectation_id = affectation_id
	from affectation 
	where employe = @employe_id;
    if exists (select 1 from affectation where affectation_id = @affectation_id)
    begin
        insert into prime(affectation, prime, type_prime, date_attribution, date_fin, commentaire)
        values (@affectation_id, @prime, @type_prime, @date_attribution, @date_fin, @commentaire);
    end
end
--                          procedure pour modifier une prime
go
create procedure modprime
    @prime_id int,
    @employe_id int,
    @prime decimal(10,2),
    @type_prime nvarchar(50) null,
    @date_attribution date,
    @date_fin date null,
    @commentaire nvarchar(500) null
as
begin
	set nocount on;
	declare @affectation_id int
	select @affectation_id = affectation_id
	from affectation 
	where employe = @employe_id;
    if exists (select 1 from prime where prime_id = @prime_id)
	and exists (select 1 from affectation where affectation_id = @affectation_id)
    begin
        update prime
        set affectation = @affectation_id,
            prime = @prime,
            type_prime = @type_prime,
            date_attribution = @date_attribution,
            date_fin = @date_fin,
            commentaire = @commentaire
        where prime_id = @prime_id;
    end
end
--                       procedure pour afficher toutes les primes

go
create procedure showprime
    @employe_id int
as
begin
	set nocount on;
    select po.titre as poste, pr.prime, pr.type_prime,
           pr.date_attribution, pr.date_fin, pr.commentaire
    from prime pr
    join  affectation a on pr.affectation = a.affectation_id
	join poste po on a.poste = po.poste_id 
    where a.employe = @employe_id;
end

go 
create procedure getprime
	@employe_id int,
    @prime decimal(10,2),
    @type_prime nvarchar(50) null,
    @date_attribution date,
    @date_fin date null,
    @commentaire nvarchar(500) null,
	@id int output
as
begin
	set nocount on;
	declare @affectation_id int
	select @affectation_id = affectation_id
	from affectation 
	where employe = @employe_id;
	select @id = prime_id
	from prime
	where affectation = @affectation_id
	and prime = @prime
	and type_prime = @type_prime
	and date_attribution = @date_attribution
	and date_fin = @date_fin
	and commentaire = @commentaire
end
---------------------------------------------------------------------------------------------------------------
--							rendez vous
--                 procedure pour ajouter un rendez vous  (khsni nzid une verification si l'employe est actif)
go
create procedure addrdv
    
    @client nvarchar(50),
	@employe nvarchar(50),
	@vehicule nvarchar(50),
    @date_rdv date,
	@heure_rdv time,
    @detail nvarchar(500) = null
as
begin
	set nocount on;
    if exists (select 1 from personne where nom+ ' '+prenom = @client)
       and exists (select 1 from personne where nom+ ' '+prenom = @employe)
	   and exists (select 1 from vehicule where marque+ ' '+modele = @vehicule)
    begin
		declare @client_id int,@employe_id int,@vehicule_id int
		select @client_id = personne_id
		from personne p		
		where p.nom+' '+p.prenom = @client
		select @employe_id = personne_id
		from personne p 
		where p.nom+' '+p.prenom = @employe
		select @vehicule_id = vehicule_id
		from vehicule v
		where v.marque+ ' ' +v.modele = @vehicule
		if exists (select 1 from vehicule v where v.client_id = @client_id 
					and v.marque+ ' ' +v.modele = @vehicule)
		begin
			insert into rendezvous(client_id, employe_id, vehicule_id, date_rdv, heure_rdv, detail)
			values (@client_id, @employe_id, @vehicule_id, @date_rdv, @heure_rdv, @detail);
		end

		else
		begin
			raiserror('ce véhicule n appartient pas à ce client', 16, 1);
		end
    end

	else
	begin
		raiserror('informations incorrectes', 16, 1);
	end
end
--                          procedure pou modifier un rendez vous
go
create procedure modrdv
    @rdv_id int,
    @client nvarchar(50),
	@employe nvarchar(50),
	@vehicule nvarchar(50),
    @date_rdv date,
	@heure_rdv time,
    @detail nvarchar(500)
as
begin
	set nocount on;
    if exists (select 1 from rendezvous where rdv_id = @rdv_id)
	   and exists (select 1 from personne where nom+ ' '+prenom = @client)
       and exists (select 1 from personne where nom+ ' '+prenom = @employe)
	   and exists (select 1 from vehicule where marque+ ' '+modele = @vehicule)
    begin
		declare @client_id int,@employe_id int,@vehicule_id int
		select @client_id = personne_id
		from personne p		
		where p.nom+' '+p.prenom = @client
		select @employe_id = personne_id
		from personne p 
		where p.nom+' '+p.prenom = @employe
		select @vehicule_id = vehicule_id
		from vehicule v
		where v.marque+ ' ' +v.modele = @vehicule
		if exists (select 1 from vehicule v where v.client_id = @client_id 
					and v.marque+ ' ' +v.modele = @vehicule)
		begin
			update rendezvous
					set client_id = @client_id,
						employe_id = @employe_id,
						vehicule_id = @vehicule_id,
						date_rdv = @date_rdv,
						heure_rdv = @heure_rdv,
						detail = @detail
					where rdv_id = @rdv_id;
		end			
		else
		begin
			raiserror('ce véhicule n appartient pas à ce client', 16, 1);
		end
    end

	else
	begin
		raiserror('informations incorrectes', 16, 1);
	end
end
--                     procedure pour afficher les rendez vous
go
create procedure rechercher_rdv   

	@critere nvarchar(100) = null
as
begin
	set nocount on;
    select r.date_rdv, convert(nvarchar(5), r.heure_rdv ,108) as heure_rdv -- pour avoir que le format hh:mm:ss
			, c.nom+' '+c.prenom as client,
			e.nom+' '+e.prenom as employe, v.marque+' '+v.modele as vehicule, r.detail
    from rendezvous r
	join personne c on r.client_id = c.personne_id
	join personne e on r.employe_id = e.personne_id
	join vehicule v on r.vehicule_id = v.vehicule_id
	where @critere is null
        or c.nom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or c.prenom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or e.nom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or e.prenom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or v.marque collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or v.modele collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
end
-- procedure qui retourne id du rendez vous 
go 
create procedure getrdv
	@client nvarchar(50),
	@employe nvarchar(50),
	@vehicule nvarchar(50),
    @date_rdv date,
	@heure_rdv time,
	@id int output
as
begin
	set nocount on;
	declare @client_id int,@employe_id int,@vehicule_id int
		select @client_id = personne_id
		from personne p		
		where p.nom+' '+p.prenom = @client
		select @employe_id = personne_id
		from personne p 
		where p.nom+' '+p.prenom = @employe
		select @vehicule_id = vehicule_id
		from vehicule v
		where v.marque+ ' ' +v.modele = @vehicule
	select @id = vehicule_id
    from rendezvous
    where client_id = @client_id
	and employe_id = @employe_id
	and vehicule_id = @vehicule_id
	and date_rdv = @date_rdv
	and heure_rdv = @heure_rdv
	
end
-----------------------------------------------------------------------------------------------------
--                                  vehicule
--               procedure pour ajouter un vehicule
go
create procedure addvehicule
    @proprietaire nvarchar(50),
    @couleur nvarchar(20),
	@kilometrage decimal(9,2),
    @num_chassi nvarchar(20),
	@matricule nvarchar(15),
    @marque nvarchar(30),
	@modele nvarchar(30),
	@carburant nvarchar(20),
	@annee_fabrication int
as
begin
	set nocount on;
    if exists (select 1 from personne where nom + ' ' + prenom = @proprietaire)
    begin
		declare  @client_id int
		select @client_id = personne_id
		from personne p		
		where p.nom+' '+p.prenom = @proprietaire

        insert into vehicule(client_id, couleur, kilometrage, num_chassi, matricule, marque, modele, carburant, annee_fabrication)
        values (@client_id, @couleur, @kilometrage, @num_chassi, @matricule, @marque, @modele, @carburant, @annee_fabrication);
    end
	else
	begin
		raiserror('Client inexistant', 16, 1);
	end
end
--                    procedure pour modifier une voiture
go
create procedure modvehicule
	@vehicule_id int,
    @proprietaire nvarchar(50),
    @couleur nvarchar(20),
	@kilometrage decimal(9,2),
    @num_chassi nvarchar(20),
	@matricule nvarchar(15),
    @marque nvarchar(30),
	@modele nvarchar(30),
	@carburant nvarchar(20),
	@annee_fabrication int
as
begin
	set nocount on;
    if exists (select 1 from personne where nom + ' ' + prenom = @proprietaire)
    begin
		declare  @client_id int
		select @client_id = personne_id
		from personne p		
		where p.nom+' '+p.prenom = @proprietaire
        update vehicule
        set client_id = @client_id,
			couleur = @couleur,
			kilometrage = @kilometrage,
			num_chassi = @num_chassi,
			matricule = @matricule,
			marque = @marque,
			modele = @modele,
			carburant = @carburant,
			annee_fabrication = @annee_fabrication
        where vehicule_id = @vehicule_id;
    end
	else
	begin
		raiserror('Client inexistant', 16, 1);
	end
end
--                        procedure pour rechercher les vehicules
go
create procedure rechercher_vehicule
    @critere nvarchar(100) = null
as
begin
	set nocount on;
    select p.nom + ' ' + p.prenom as proprietaire, v.*
    from vehicule v
    join client c on v.client_id = c.client_id
	join personne p on c.client_id = p.personne_id
    where 
		@critere is null
        or v.marque collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or v.carburant collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or v.couleur collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or v.matricule collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or v.modele collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or p.nom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or p.prenom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
end

go
create procedure supvehicule
	@id int
as 
begin
	set nocount on;
    delete from vehicule where vehicule_id = @id;
end
-- procedure qui retourne l'id d'un vehicule
go 
create procedure getvehicule
    @couleur nvarchar(20),
	@kilometrage decimal(9,2),
    @num_chassi nvarchar(20),
	@matricule nvarchar(15),
    @marque nvarchar(30),
	@modele nvarchar(30),
	@carburant nvarchar(20),
	@annee_fabrication int,
	@id int output
as
begin
	set nocount on;
    select @id = vehicule_id
    from vehicule
    where couleur = @couleur
      and kilometrage = @kilometrage
      and num_chassi = @num_chassi
	  and matricule = @matricule
	  and marque = @marque
	  and modele = @modele
	  and carburant = @carburant
	  and annee_fabrication = @annee_fabrication
end
-----------------------------------------------------------------------------------------------------------------
--                          horaire travaille
--                         procedure pour ajouter heurs de travaille
go
create procedure addheur
    @employe_id int,
    @jour_semaine tinyint,
	@heure_debut time,
    @heure_fin time	
as
begin
	set nocount on;
    if exists (select 1 from employe where employe_id = @employe_id)
    begin
        insert into horaire_employe(employe_id, jour_semaine, heure_debut, heure_fin)
        values (@employe_id, @jour_semaine, @heure_debut, @heure_fin);
    end
end
--                         procedure pour modifier un horaire
go
create procedure modheur
	@horaire_id int,
    @employe_id int,
    @jour_semaine tinyint,
	@heure_debut time,
    @heure_fin time
as
begin
	set nocount on;
    if exists (select 1 from employe where employe_id = @employe_id)
    begin
        update horaire_employe
        set employe_id = @employe_id,
            jour_semaine = @jour_semaine,
            heure_debut = @heure_debut,
			heure_fin = @heure_fin
        where horaire_id = @horaire_id;
    end
end
--                  afficher horraire
go 
create procedure afficherheur
	@employe_id int
as
begin
	set nocount on;
	select p.nom, p.prenom, h.*
	from horaire_employe h
	join employe e on h.employe_id = e.employe_id
	join personne p on e.employe_id = p.personne_id
	where h.employe_id = @employe_id;
end

go
create procedure getheur
    @employe_id int,
    @jour_semaine tinyint,
	@heure_debut time,
    @heure_fin time,
	@id int output
as
begin
	set nocount on;
	select @id = horaire_id
	from horaire_employe
	where employe_id = @employe_id
	and jour_semaine = @jour_semaine
	and heure_debut = @heure_debut
	and heure_fin = @heure_fin
end

--------------------------------------------------------------------------------------------------
--                  depot 
go 
create procedure adddepot
	@num nvarchar(50) ,
	@rayon nvarchar(15) ,
	@etagere nvarchar(15) ,
	@adresse nvarchar(100) , 
	@descript nvarchar(500) = null
as
begin
	set nocount on;
    if exists (select 1 from addresse where designation = @adresse)
    begin
		declare @id int
		select @id = addresse_id from addresse a where a.designation = @adresse;
        insert into depot(num, rayon, etagere, adresse, descript)
        values (@num, @rayon, @etagere, @id, @descript);
    end
	else
	begin
		raiserror('Adresse inexistante', 16, 1);
	end
end

go 
create procedure moddepot
	@depot_id int,
	@num nvarchar(50) ,
	@rayon nvarchar(15) ,
	@etagere nvarchar(15) ,
	@adresse nvarchar(100) , 
	@descript nvarchar(500) = null
as
begin
	set nocount on;
    if exists (select 1 from addresse where designation = @adresse)
    begin
		declare @id int
		select @id = addresse_id from addresse where designation = @adresse
        update depot
		set num = @num,
			rayon = @rayon,
			etagere = @etagere,
			adresse = @id,
			descript = @descript
		where depot_id = @depot_id
    end
	else
	begin
		raiserror('Adresse inexistante', 16, 1);
	end
end

go 
create procedure showdepot
as
begin
	set nocount on;
	select d.num, d.rayon, d.etagere , w.nom_wilaya + ' ' + da.nom_daira + ' ' + c.nom_commune + ' ' + r.nom_Rue as localisation, descript 
	from depot d
	join addresse a on d.adresse = a.addresse_id
	JOIN Rue r ON a.Rue = r.rue_id
	JOIN commune c ON r.commune= c.commune_id
	JOIN daira da ON c.daira = da.daira_id
	JOIN wilaya w ON da.wilaya = w.wilaya_id
		  
end

go
create procedure getdepot
	@num nvarchar(50) ,
	@rayon nvarchar(15) ,
	@etagere nvarchar(15) ,
	@id int output
as
begin
	set nocount on;
	select @id = depot_id
	from depot
	where num = @num
	and rayon = @rayon
	and etagere = @etagere
end

------------------------------------------------------------------------------------------------
--                             produit
go 
create procedure addproduit
	@emplacement nvarchar(100),
	@ref nvarchar(10),
	@prix decimal(10,2),
	@nom nvarchar(100),
	@quantite int,
	@descript nvarchar(500) = null
as
begin
	set nocount on;
    if exists (select 1 from depot where num + ' ' + rayon + ' ' + etagere = @emplacement)
    begin
		declare @id int
		select @id = depot_id 
		from depot
		where num + ' ' + rayon + ' ' + etagere = @emplacement
        insert into produit(depot, ref, prix, nom, quantite, descript)
        values (@id, @ref, @prix, @nom, @quantite, @descript);
    end
	else
	begin
		raiserror('Emplacement inexistante', 16, 1);
	end
end

go 
create procedure moddproduit
	@produit_id int,
	@emplacement nvarchar(100),
	@ref nvarchar(10),
	@prix decimal(10,2),
	@nom nvarchar(100),
	@quantite int,
	@descript nvarchar(500) = null
as
begin
	set nocount on;
     if exists (select 1 from depot where num + ' ' + rayon + ' ' + etagere = @emplacement)
    begin
		declare @id int
		select @id = depot_id from depot where num + ' ' + rayon + ' ' + etagere = @emplacement
        update produit
		set depot = @id,
			ref = @ref,
			prix = @prix,
			nom = @nom,
			quantite = @quantite,
			descript = @descript
		where produit_id = @produit_id
    end
	else
	begin
		raiserror('Emplacement inexistante', 16, 1);
	end
end


go
create procedure getproduit
	@ref nvarchar(10),
	@prix decimal(10,2),
	@nom nvarchar(100),
	@id int output
as
begin
	set nocount on;
	select @id = produit_id
	from produit
	where ref = @ref
	and prix = @prix
	and nom = @nom
end

go
create procedure rechercher_produit
    @critere nvarchar(100) = null
as
begin
	set nocount on;
    select p.ref, p.nom, p.prix, p.quantite, d.num+' '+d.rayon+' '+d.etagere as emplacement, p.descript  
	from produit p
	join depot d on p.depot = d.depot_id
    where 
		@critere is null
        or p.ref collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.nom collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or p.descript collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
        or d.num collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or d.rayon collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
		or d.etagere collate SQL_Latin1_General_CP1_CI_AS like '%' + @critere + '%'
end

create procedure supproduit
	@id int
as
begin
	delete from produit where produit_id = @id
end