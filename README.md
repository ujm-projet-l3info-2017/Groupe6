# Projet tutoré
## RazBot - ChatBot Cinéma

Chatbot capable de chercher un film pour un utilisateur et de donner des informations sur le film

### Utilisation 

Le programme nécéssite un fichier settings.ini qui contient les ID confidentiels d'accès à la page Facebook (non présent sur GitHub)
Le chemin est à configurer dans `/src/facebook/Token.java`

Il y a deux modes de fonctionnements (à configurer dans settings.ini):
* Un mode à vérification de nouveaux messages par intervalles réguliers (Intervalles à configurer dans settings.ini)
* Un mode à Webhook qui recoit des requêtes POST de Facebook (Ne fonctionne que sur le serveur derrière https://razbot.jeamme.fr/)

Le programme peut également être lancé dans la console via `/src/ia/TestIA.java`

### Fonctionnalités 

* Recherche d'un film par critères
  * genre et/ou récent
  * acteur/réalisateur
* Informations sur un film
  * réalisateur
  * acteurs principaux
  * affiche
  * genre
  * avis (en fonction des notes du public et de la presse sur allocine.fr)
  * synopsis

### Schéma de la conversation
![Structure de la conversation](/AutomateStructure.png)
