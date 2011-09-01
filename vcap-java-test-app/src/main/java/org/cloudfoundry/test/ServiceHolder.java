/*
 * Copyright 2009-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.cloudfoundry.test;

import java.util.Properties;

import javax.sql.DataSource;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.document.mongodb.MongoDbFactory;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.stereotype.Component;

/**
 * Container for auto-wired dependencies. Serves as verification of proper
 * dependency resolution by AppContext
 *
 * @author Jennifer Hickey
 *
 */
@Component
public class ServiceHolder {

	private final Logger log = LoggerFactory.getLogger(ServiceHolder.class);

	@Autowired(required=false)
	private MongoDbFactory mongoDbFactory;

	@Autowired(required=false)
	private ConnectionFactory rabbitConnectionFactory;

	@Autowired(required=false)
	private RedisConnectionFactory redisConnectionFactory;

	//Qualifier doesn't seem to work if Autowired is not required.  Need to investigate further if we add a profile that doesn't have a MySQL DataSource
	@Autowired
	@Qualifier("test_mysql_database")
	private DataSource mysqlDataSource;

	@Autowired(required=false)
	@Qualifier("serviceProperties")
	private Properties serviceProperties;

	@Autowired(required=false)
	@Qualifier("test_postgres_database")
	private DataSource postgresDataSource;

	public MongoDbFactory getMongoDbFactory() {
		return mongoDbFactory;
	}

	public ConnectionFactory getRabbitConnectionFactory() {
		return rabbitConnectionFactory;
	}

	public RedisConnectionFactory getRedisConnectionFactory() {
		return redisConnectionFactory;
	}

	public BasicDataSource getMySqlDataSource() {
		return (BasicDataSource) mysqlDataSource;
	}

	public Properties getServiceProperties() {
		log.info("Retrieving service properties: " + serviceProperties);
		return serviceProperties;
	}

	public BasicDataSource getPostgresDataSource() {
		return (BasicDataSource) postgresDataSource;
	}
}
