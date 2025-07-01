
-- table personne
create table personne (
    personne_id int identity primary key,
    nom nvarchar(50) unique not null,
    prenom nvarchar(50) not null,
    email nvarchar(100) unique not null,
    phone varchar(30) unique not null  
);

-- table poste
create table poste (
    poste_id int identity primary key,
    titre nvarchar(50) unique not null,
    salaire_base decimal(8,2) not null,
    description_poste nvarchar(500)
);

-- table employe
create table employe (
    employe_id int primary key references personne(personne_id),
    statut nvarchar(8) check (statut in ('actif', 'inactif')) not null,
    matricule nvarchar(5) unique not null,
    num_securite_sociale nvarchar(20) unique not null,
	heures_travail_par_jour decimal(4,2) not null,
	addresse nvarchar(50),                    --int references addresse(addresse_id),
    lieu_naissance nvarchar(50),              --int references wilaya(wilaya_id) not null,
    date_naissance date not null,
    date_recrutement date not null,
	details nvarchar(500) 
);
-- table horaire travaille 
create table horaire_employe (
    horaire_id int identity primary key,
    employe_id int references employe(employe_id) not null,
    jour_semaine tinyint CHECK (jour_semaine between 1 and 7) not null, -- 1=Lundi, 7=Dimanche
    heure_debut time,
    heure_fin time
);


-- table affectation
create table affectation (
    affectation_id int identity primary key,
    employe int references employe(employe_id) not null,
    poste int references poste(poste_id) not null,
    date_debut date not null,
    date_fin date,
    salaire_negocie decimal(10,2)
);

-- table prime
create table prime (
    prime_id int identity primary key,
    affectation int references affectation(affectation_id) not null,                                         
    prime decimal(10,2) not null,
    type_prime nvarchar(50) ,
    date_attribution date not null,
    date_fin date ,
    commentaire nvarchar(500)
);

-- table client
create table client (
    client_id int primary key references personne(personne_id),
	num_client int identity not null ,
    type_client nvarchar(15) check (type_client in ('individuel', 'entreprise')) not null,
    date_inscription date not null,
    details_client nvarchar(500)
);
-- table vehicule
create table vehicule(
	vehicule_id int identity(1,1) primary key,
	client_id int references client(client_id) on delete set null,
	couleur nvarchar(20) not null,
	kilometrage decimal(9,2) not null,
	num_chassi nvarchar(20) unique not null,
	matricule nvarchar(15) unique not null ,
	marque nvarchar(30) not null,
	modele nvarchar(30) not null,
	carburant nvarchar(20) not null,
	annee_fabrication int  
);
-- table redez-vous
create table rendezvous (
    rdv_id int identity primary key,
	client_id int references client(client_id) on delete set null,
    employe_id int references employe(employe_id) not null,
	vehicule_id int references vehicule(vehicule_id) on delete set null,
	date_rdv date not null,
	heure_rdv time not null,
    detail nvarchar(500)
);

create table depot(
	depot_id int identity primary key,
	num nvarchar(50) not null,
	rayon nvarchar(15) not null,
	etagere nvarchar(15) not null,
	adresse int references addresse(addresse_id), 
	descript nvarchar(500)

); 

create table produit(
	produit_id int identity primary key,
	depot int references depot(depot_id),
	ref nvarchar(10) not null,
	prix decimal(10,2) not null,
	nom nvarchar(100) not null,
	quantite int not null,
	descript nvarchar(500)
);
-- ki ndir supclient num_client radi ysra fiha decalage nkhaliha hak bach tbyn bli mhina un client wla nbdl la numerotation ? bsh raha identity 

exec rechercher_client im;

exec addclient benseban,abdelah,'chaigi@gmail.com',0588479231,individuel,'2025-5-31','je suis le 3eme client';

exec showclientdetails 1;

exec modclient 4,farsi,hichem,'hichemfrs@gmail.com',058878522,individuel,'2025-5-28','je suis le 4eme client';

declare @id int;
exec getpersonne farsi,hichem,'hichemfrs@gmail.com',058878522,@id = @id output;
select @id as id_client;

exec supclient 3;

exec modemploye 12,'lzrag',nadji,'bleu@gmail.com',0688592347,'actif',335877624511,10,'oued souf',Oran,'1954-2-2','2025-1-1','je suis actif';
                   
exec rechercher_employe ;

exec showemployedetails ;

declare @id int;
exec getpersonne 'lzrag',nadji,'bleu@gmail.com',0688592347,@id = @id output;
select @id as id_employe;

exec addvehicule 'Farsi hichem', 'gris souris', 327000,'95rr7yzb6', '337154 105 31', hyunday, atos, essance, 2005

exec rechercher_vehicule

exec modvehicule 1, 'Gherras imed', 'noire', 147000,'45m87po21', '21115 115 31', dacia, stepway, essance, 2015

exec supvehicule 4

declare @id int;
exec getvehicule 'noire', 147000,'45m87po21', '21115 115 31', dacia, stepway, essance, 2015, @id = @id output;
select @id as vehicule_id

exec addrdv 'Farsi hichem', 'lacheb djilali','hyunday atos', '2025-06-5', '11:30:00','rendez-vous pour remplacer les bougies' 

exec modrdv 1, 'Gherras imed', 'safir achraf','dacia stepway', '2025-06-10', '14:30:00','rendez-vous pour reparer la tole' 

exec rechercher_rdv hichem;

declare @id int;
exec getrdv 'Gherras imed', 'safir achraf','dacia stepway', '2025-06-10', '14:30:00',@id = @id output;
select @id as id_rdv;

exec addposte tolier, 25000, 'tolier experimenté minimum de 2 ans';

exec addaffectation 11, 1, '2025-05-13', null, 20000;
select * from affectation

exec showaffectation 11;

exec adddepot D1, R2, E1, 'depot 1'

insert into depot(num, rayon, etagere, adresse,descript) values('D1', 'R1', 'E3', 1, null)

exec showdepot