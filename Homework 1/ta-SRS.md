# Traffic Assistant

Traffic assistant is a web-based utility application that allows users to report traffic conditions. It is an intuitive and easy-to-use application from which a user can report events that happen on the roads within a click. These events include traffic incidents, traffic jams, closed or under-construction roads, road slipperiness, fog and poor visibility, road patrols, speed radars etc. Every user that is logged in is presented with a map where they can see other users&#39; reports. That way the user is informed about the state of the traffic around them and can decide whether it is a good idea to avoid certain roads. The collective goal of this app is to enhance the safety of the traffic participants as well as helping people lose less time on the road, increasing the satisfaction on every person behind the wheel.

# Non-functional requirements

1. The system shall conform the law for protection of personal data.
2. The system shall withstand at least 500,000 online users at the same time in the first release.
3. The system shall require no training time for the users.
4. The system shall be available 99.5% of the time during a period of one year.
5. The system shall require maintenance no more than once a year.
6. The system&#39;s interface shall take no more than 5 seconds to fully load on an average 4G
 Internet connection.

# Functional requirements

1. The system shall run in a web-based environment.
2. The system shall be compatible with Windows, macOS, Linux, iOS and Android operating systems.
3. The system shall store user accounts.
4. The system shall authenticate every user.
5. The system shall store user input as a traffic event.
6. The system shall provide a form for all users to report traffic events.
7. The system shall provide a form for all users to confirm the existence of a report.
8. The system shall store events with map coordinates and event type.
9. The system shall predict the correctness of an event by comparing other users&#39; inputs.
10. The system shall store events based on an infrastructure map.
11. The system shall use OpenStreetMap as a source for an infrastructure map.
12. The system shall allow storing events only near roads on the map.
13. The system shall present the events on the map to all users.
14. The system shall store events for one hour.
15. The system shall update the expiration time of an event when another user confirms it.
