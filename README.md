#Projet tutor�
## RazBot - ChatBot Cin�ma

Le programme n�c�ssite un fichier settings.ini qui contient les ID confidentiels d'acc�s � la page Facebook (non pr�sent sur GitHub)
Le chemin est � configurer dans `/src/facebook/Token.java`

Il y a deux modes de fonctionnements (� configurer dans settings.ini):
* Un mode � v�rification de nouveaux messages par intervalles r�guliers (Intervalles � configurer dans settings.ini)
* Un mode � Webhook qui recoit des requ�tes POST de Facebook (Ne fonctionne que sur le serveur derri�re https://razbot.jeamme.fr/)