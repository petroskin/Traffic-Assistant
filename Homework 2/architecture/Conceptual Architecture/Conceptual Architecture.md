## Traffic Assistant - Conceptual Architecture

+ App UI
	+ Graphical user interface and views to present
	+ Data and model handled by the application logic
	+ Remote method calls to the application logic
	+ Presentation layer

+ Admin Panel
	+ User interface with broader privileges
	+ Data and model handled by the app logic

+ App Logic
	+ Transform data as necessary
	+ Route data from application to users
	+ Handle requests
	+ Application logic layer

+ Data Storage
	+ Store road coordinates
	+ Store user data and accounts
	+ Store event data
	+ Update regularly through data flow architecture
	+ Data layer

+ User Manager
	+ Manage sessions
	+ Manage user data and events by the given user
	+ Part of application logic layer

+ Map Display
	+ Create interactive map from OpenStreetMaps API
	+ Give maps to app logic
	+ Part of application logic layer
