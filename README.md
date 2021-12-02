API-2021-SMTP: Lucien Perregaux, Maxim Golay
--------------------------------------------

### Brief

Ce projet a pour but d'envoyer une campagne de plaisanteries par mail.
L'application est écrite en Java et ets entièrement paramètrable via les fichiers de configs qu'elle offre.
L'application propose aussi un serveur bidon, utilisant Docker, pour voir le résultat de son exécution.


### Installation du serveur bidon

L'installation du serveur bidon nécessite d'avoir une installation de Docker fonctionnelle au préalable.

***N.B.*** Les scripts fournis sont utilisables directement depuis *Linux* & *MacOS*.  
Pour Windows, veuillez utiliser une couche de compatibilité Linux (tel que *MSYS*) ou utiliser *Docker for WSL*

Pour installer le serveur,  

1. Naviguer jusqu'au dossier `docker\` à la racine du projet.
2. Lancer le script `build-image.sh` qui va construire l'image Docker du serveur.     
3. Lancer le script `run-container.sh` qui va créer une nouvelle instance de l'image.

Une fois le serveur fonctionnel, les ports 25 et 8282 sont ouverts. Ils permettent
respectivement d'intéragir avec le serveur via le protocole SMTP et voir, sur une
interface web, les mails envoyés au serveur.


### Configuration de l'application

Tous les fichiers de configuration se trouvent dans le dossier `config\` à la racine du projet.
On y retrouve 3 fichiers, dont

- messages.utf8 : Contient la pool de messages que l'application peut envoyer, séparés par `==`.
  Chaque message doit commencer par `Subject: xxx<CRLF><CRLF>`, où `xxx` est le sujet de l'email.
- victims.utf8 : Contient la liste des emails des victimes, séparés par `<CRLF>`
- settings.properties : Contient les autres paramètres de l'application sous forme de paires `clé=valeur`, à savoir
  - host : Le serveur SMTP à viser
  - port : Le port du serveur SMTP, `25` en règle générale
  - nbgroups : Le nombre de groupes de mail à faire


### Fonctionnement

L'application commence par s'assurer qu'il y a au moins 3 fois plus de victimes que de *nbgroups*,
c.à.d. que chaque groupe est au moins constitué de 3 membres.

L'application choisit ensuite parmi la liste des victimes *nbgroups* adresses mails qui seront les *envoyeurs*.
Elle constitue ensuite des groupes aléatoires qui sont des partitions des victimes, c.à.d. qu'une victime
n'apparait **qu'une et une seule fois** dans **un et un seul groupe** par campagne.

Enfin, chaque *envoyeur* envoie un mail à un *groupe* différent. Dans le header du mail, le champ `Sender: `
utilisera l'email de l'envoyeur, usurpant ainsi son identité.