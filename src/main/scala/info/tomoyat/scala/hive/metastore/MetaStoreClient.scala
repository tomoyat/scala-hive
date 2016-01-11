package info.tomoyat.scala.hive.metastore

import org.apache.hadoop.hive.metastore.api.{Role, PrincipalType, Database, ThriftHiveMetastore}
import org.apache.hadoop.hive.metastore.api.ThriftHiveMetastore.Client
import org.apache.thrift.protocol.TBinaryProtocol
import org.apache.thrift.transport.TSocket

import scala.collection.JavaConversions._

class MetaStoreClient(ip: String, port: Int) {
    val transport = new TSocket(ip, port)
    transport.open()
    val protocol = new TBinaryProtocol(transport)
    val metaClient = new Client(protocol)

    def createDatabase(name: String, ownerRole: String): Unit = {
        val db = new Database
        db.setName(name)
        db.setOwnerName(ownerRole)
        db.setOwnerType(PrincipalType.ROLE)

        metaClient.create_database(db)
    }

    def createDatabaseWithLocation(name: String, ownerRole: String, location: String): Unit = {
        val db = new Database
        db.setName(name)
        db.setOwnerName(ownerRole)
        db.setOwnerType(PrincipalType.ROLE)
        db.setLocationUri(location)

        metaClient.create_database(db)
    }

    def getDatabases(): Seq[String] = {
        metaClient.get_all_databases()
    }

    /*
    grantRoleToUser("alice", "alice")
    mysql> select * from ROLE_MAP where PRINCIPAL_NAME = 'alice';
+---------------+------------+--------------+---------+--------------+----------------+----------------+---------+
| ROLE_GRANT_ID | ADD_TIME   | GRANT_OPTION | GRANTOR | GRANTOR_TYPE | PRINCIPAL_NAME | PRINCIPAL_TYPE | ROLE_ID |
+---------------+------------+--------------+---------+--------------+----------------+----------------+---------+
|             6 | 1452528161 |            0 | admin   | ROLE         | alice          | USER           |      11 |
+---------------+------------+--------------+---------+--------------+----------------+----------------+---------+
1 row in set (0.00 sec)
     */
    def grantRoleToUser(role: String, userName: String): Unit = {
        val admin = "admin"
        metaClient.grant_role(role, userName, PrincipalType.USER, admin, PrincipalType.ROLE, false)
    }

    /*
     metadataはこんな感じ
desc ROLES;
+-------------+--------------+------+-----+---------+-------+
| Field       | Type         | Null | Key | Default | Extra |
+-------------+--------------+------+-----+---------+-------+
| ROLE_ID     | bigint(20)   | NO   | PRI | NULL    |       |
| CREATE_TIME | int(11)      | NO   |     | NULL    |       |
| OWNER_NAME  | varchar(128) | YES  |     | NULL    |       |
| ROLE_NAME   | varchar(128) | YES  | UNI | NULL    |       |
+-------------+--------------+------+-----+---------+-------+
     */
    def createRole(roleName: String): Unit = {
        val role = new Role
        role.setRoleName(roleName)
        role.setCreateTime((System.currentTimeMillis() / 1000).toInt)
        metaClient.create_role(role)
    }

    def getRoles(roleName: String): Unit = {
        val roles = metaClient.list_roles(roleName, PrincipalType.ROLE)
        for (role <- roles) {
            println(role)
        }
    }

}
