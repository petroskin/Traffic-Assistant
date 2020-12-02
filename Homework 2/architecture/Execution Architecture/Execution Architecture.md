## Traffic Assistant - Execution Architecture

+ Client Side Application Logic
	+ The application's web interface as presented to the user
	+ Client side request sender and request handler
	+ Remote method invocation to the application

+ Server Navigation Logic
	+ Request handler and data router for the server side
	+ The main relation between components
	+ Connects the server to the client
	+ Coordinates client side view with up-to-date data
	+ Functions as MVC controller

+ View Creator
	+ Generates views for the user
	+ Gets data through the navigation component
	+ Functions as MVC view

+ Map Display Generator
	+ Generates the interactive map for the user
	+ Communicates with OpenStreetMaps API for the map
	+ Communicates with the data layer for nodes and events
	+ Implemented as a service
	+ Greatly comprises the MVC model passed to the view creator

+ Event Manager
	+ Coordinates creation of new events
	+ Provides event information
	+ Manages existing events by iteslf as an active component
	+ Communicates with data layer through CRUD functionalities

+ Notification Component
	+ Activated on addition or modification of events
	+ Notifies system of update to the events

+ Data Storage
	+ Stores map nodes
	+ Stores events
	+ implements CRUD functionalities
	+ Works as a service, managing data through requests
