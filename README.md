# Redd-ish

A Spark Java implementation of a reddit-like service

Made for the DAT250-course on [HVL](https://hvl.no) november of 2018 by
[Zorrix](https://github.com/magnusEdva), [GuMMaN](https://github.com/gummangummangumman), [fhqwhgadss](https://github.com/fhqwhgadss) and [lauritzv](https://github.com/lauritzv)

NB! Don't use this as-is due to low password safety!


![Screenshot](/screenshot.gif?raw=true "screenshot")


To run Redd-ish an SQL server is required. Redd-ish runs using Hibernate configured with a hibernate.cfg.xml file inside the /resources/ directory. To succesfully compile Redd-ish a hibernate.cfg.xml file specifying your database of choice must be provided. The file looks like this:

```xml
<?xml version='1.0' encoding='utf-8'?>
<!DOCTYPE hibernate-configuration PUBLIC "-//Hibernate/Hibernate Configuration DTD//EN"
        "http://www.hibernate.org/dtd/hibernate-configuration-3.0.dtd">

<hibernate-configuration>
    <session-factory>
        <property name="connection.url">JDBC adress here;create=true</property>
        <property name="dialect">SQL Dialect here</property>
        <property name="hibernate.connection.username">username</property>
        <property name="hibernate.connection.password">password</property>
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

Hibernate will then automatically generate the tables required when running for the first time.
