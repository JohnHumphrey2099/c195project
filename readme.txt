Title: Customer Appointment Manager
The purpose of this application is to allow users to create, save, and manage customers and their appointments.
Author: John Humphrey
Contact: jhump98@wgu.edu
Application Version 1.0
Date 2/23/2023
IntelliJ Community 2022.2
Java 17.0.5
JavaFX-SDK 17.0.2
mysql:mysql-connector-java-8.0.30
Additional report: A total of the first level divisions for each country.
Lambda Locations: AppointmentScreenController.generateAppointmentTimes() / AppointmentScreenController.saveNewButton()

Directions

Log-in and Main Menu Screens:
After extracting the zip file to your chosen location, import the project into your IDE.
Run the project using an “Application” Run / Debug configuration.
Enter “test” in both the username and password fields,  then click the “log-in” button to log in.
You should see the main menu with buttons to go to the Customers screen, the Appointments screen, the Reports screen, along with the exit button which will close the application.

Customer Screen:
The customer screen contains a table of all customers and their properties. To make changes to an existing customer, select one from the table and click the “Edit Customer” button.
After making changes, you can choose “Save Changes”  to overwrite the existing customer, or “Save as New Customer” which will create a new entry in the database.
If you want to refresh the screen without saving anything, click the clear button.
You can also create a new customer from scratch by entering data into all the fields, choosing a country and division from the drop-down menus, and then clicking “Save as New Customer”.
The application will not let you use the “Save Changes” button to save unless you are updating an existing customer.
To delete a customer, select one from the table and click “Delete Customer”. This will also delete any appointments the customer may have had scheduled.
The “Back” button will return to the main menu.

Appointments Screen:
The Appointments screen is where you can view all customer appointments, appointments for the current week, or appointments for the current month by using the radio buttons.
To make changes to an existing appointment, select one from the table and click the “Edit Appointment” button.
After making changes, you can choose “Save Changes” to overwrite the existing appointment, or “Save as New Appointment” which will create a new entry in the database.
If you want to refresh the screen without saving the entered data, click the clear button.
You can also create a new appointment by entering data into all the fields, choosing a Customer, Contact, User, Appointment Date, Start Time, and End Time from the drop-down menus, and then clicking “Save as New Appointment”.
The application will not let you use the “save changes” button to save unless you are updating an existing appointment.
To delete an appointment, select one from the table and click “Delete Appointment”. The “Back” button will return to the main menu.

Reports Screen:
The Reports screen shows the total number of appointments, grouped by month and type, as well as the total number of first level divisions grouped by country.
There is also a contact schedule where all appointments for a selected contact will be shown.
