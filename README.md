# groep-5

Het project importeren
----------------------

1. Open NetBeans.
2. Ga naar Team > Git > Clone.
3. Geef respository URL, user en password in.
4. Selecteer een map om het project in te plaatsen.
5. Klik een eerste maal op next en selecteer de master branch. Klik vervolgens opnieuw op next.
6. Klik op Finish. Van zodra de clone klaar is, krijg je een pop-up. Klik open project.
7. Bij problemen: Indien er 'resolve project' komt op org.json.jar, klik resolve project en navigeer naar de map van het project. Klik de jar aan.

Databank opzetten
-----------------

1. Navigeer naar het Services tabblad. Klik rechts op Java DB (onder Databases) en selecteer Create Database.
2. Geef volgende informatie in:
	* Database Name: jpadb
	* User Name: iii
	* Password: iiipwd
	* Confirm Password: iiipwd
3. Klik OK. De Databank wordt nu opgezet.
4. Dubbelklik op jdbc:derby://localhost:1527/jpadb [iii on III] om met de databank te connecteren.

Project initialiseren
---------------------

1. Navigeer naar tabblad Projects en open ProjectCoin.
2. Run de Test File ProjectCoinTest. De databank wordt opgevuld.
3. Klik refresh op de databank om de toegevoegde data aan de databank weer te geven.
4. Deze Test File bevatten alle testen en illustreert ook hoe de methodes werken.
5. Het project bevat voldoende commentaar die de methodes verduidelijken.
