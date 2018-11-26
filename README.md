# Redd-ish

A Spark Java implementation of a reddit-like service

Made for the DAT250-course on [HVL](https://hvl.no) november of 2018 by
[Zorrix](https://github.com/magnusEdva), [GuMMaN](https://github.com/gummangummangumman), [fhqwhgadss](https://github.com/fhqwhgadss) and [lauritzv](https://github.com/lauritzv)


![Screenshot](/screenshot.gif?raw=true "screenshot")

## How to run it

1. Clone the repository.

2. Add a hibernate.cfg.xml file in the /resources/ folder (see below)

3. Execute `mvn compile exec:java` in the root directory OR run the project in an IDE

4. Open in your browser `https://localhost:4567/`

## Adding the hibernate file


To run Redd-ish an SQL server is required. Redd-ish runs using Hibernate configured with a hibernate.cfg.xml file inside the /resources/ directory. To succesfully compile Redd-ish a hibernate.cfg.xml file specifying your database of choice must be provided. You'll find the file template below, you must change the INSERT_STUFF_HERE to match with your own SQL database.

```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <property name="connection.url">INSERT_JDBC_ADDRESS_HERE;create=true</property>
        <property name="dialect">INSERT_SQL_DIALECT_HERE</property>
        <property name="hibernate.connection.username">INSERT_USERNAME_HERE</property>
        <property name="hibernate.connection.password">INSERT_PASSWORD_HERE</property>
        <property name="show_sql">true</property>
        <property name="hbm2ddl.auto">update</property>
        <mapping class="com.reddish.model.ReddishUser" />
        <mapping class="com.reddish.model.Comment" />
        <mapping class="com.reddish.model.Subreddit" />
        <mapping class="com.reddish.model.Post" />
        <mapping class="com.reddish.model.VoteModel" />
    </session-factory>
</hibernate-configuration>
```

Hibernate will automatically generate the tables required when running for the first time.
