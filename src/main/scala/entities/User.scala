package entities

import slick.jdbc.H2Profile.api._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object User extends App {
  final case class User(
                         name: String,
                         surname: String,
                         age: Int,
                         email: String,
                         id: Long = 0L
                       )


  // Схема для таблицы "user"
  final class UserTable(tag: Tag)
    extends Table[User](tag, "user") {



    def name = column[String]("name")

    def surname = column[String]("surname")

    def age = column[Int]("age")

    def email = column[String]("email")

    def id = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def * = (name, surname, age, email,id).mapTo[User]
  }

  // Базовый запрос для таблицы User
  lazy val users = TableQuery[UserTable]

  // Данные для теста
  def freshTestData = Seq(
    User("Alex", "Dider", 23, "AlexDi@gmail.com"),
    User("Scott", "Cawton", 23, "Cawton2@gmail.com"),
    User("Lev", "Overko", 19, "lev@gmail.com"),
  )

  // Создание в памяти БД Н2
  val db = Database.forConfig("userDB")

  // Helper method for running a query in this example file:
  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2 seconds)

  // Создание таблицы user
  println("Создание таблицы БД")
  exec(users.schema.create)

  // Создание и вставка данных в БД
  println("\nВставка тест данных")
  exec(users ++= freshTestData)

  println("\nВыбор всех пользователей:")
  exec( users.result ) foreach { println }
}
