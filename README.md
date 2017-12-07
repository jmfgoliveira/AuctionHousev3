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
