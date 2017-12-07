#  AuctionHouse


#  Setup:
# 
#  Database:
#  The database is already defined by default
# and it's set to run on db.ist.utl.pt with our properties
#
#  If you desire to run it locally you have to:
# 1-install mysql-server ($ sudo apt-get install mysql-server)
# 2-configure your mysql user and your mysql password
# 3-Navigate to AuctionHouse/Database
# 4-Connect to mysql ($ mysql -u username -p password)
# 5-Create a database (create database auction_house;
# 6-Execute setup file ($ source schema.sql)
# 7-Navigate to AuctionHouse/Server/initial/src/main/resources
# 8-Edit the properties file (db.properties) according to your 
# settings

#  Run Options:
#
#  1- Run the Servers.
#
#  To run the primary server
#  Open a terminal
#  Navigate to the folder AuctionHouse/Server/initial
# and type:
# $ mvn clean install
# $ mvn compile
# $ mvn spring-boot:run

#  To run the secondary/backup server
#  Open a terminal 
#  Navigate to the folder AuctionHouse/BackupServer/initial
# and type:
# $ mvn clean install
# $ mvn compile
# $ mvn spring-boot:run
#
#
#  2- Launch website
#  
#  First, you have to add the certificate to your browser
# this depends on your browser, but it should be on the 
# settings-> Privacy and security -> Manage Certificates
# -> Import certificate. Here you'll have to browse in the
# directory AuctionHouse/Server/initial and select the file
# keystore.p12
# 
#  In the browser navigation bar type:
#  - https://localhost:8443
#  
#  This should redirect you to the website home page
#

