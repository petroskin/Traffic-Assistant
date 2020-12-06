## Traffic Assistant - Implementation Architecture

+ Web Browser
	+ Client's browser
	+ Shows the views, containing OpenLayers maps
	+ Communicates with server through HTTP

+ Web Server
	+ Implemented in Java with Spring framework
	+ Contains 3-tier architecture
	+ Topmost layer is configured with Spring MVC
	+ Uses notification architecture to notify clients of changes

+ MVC
	+ Front controller accepts the requests sent to the server
	+ Front controller also communicates with the view creator
	+ Handler communicates with the business logic layer and handles data
	+ View creator implements OpenLayers to create views
	+ Responsible for sending notification to clients

+ OpenLayers
	+ Javascript library for implementing OpenStreetMaps

+ Business Logic
	+ Middle layer between presentation and data
	+ Gets or modifies data through the persistence layer
	+ Prepares the data for MVC for the user requests
	+ Communicates with the event manager when event operations are needed
	+ Sends the notifications from the event manager to the handler

+ Event Manager
	+ Responsible for all operations involving events
	+ Uses the notification component API to create notifications
	+ Notifies whenever a change to an event is made, or a new one is created

+ Persistence Layer
	+ Connects the data storage to the web server
	+ Uses CRUD functionalities to communicate to the storage and operate on the data

+ Data Storage
	+ The place where the data is stored
