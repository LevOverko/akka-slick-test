import slick.jdbc.H2Profile.api._
import  entities.User
import scala.concurrent.Await
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global

object runUser extends App {

  // Создание в памяти БД Н2
  val db = Database.forConfig("userDB")

  // Метод который запускает запрос
  def exec[T](program: DBIO[T]): T = Await.result(db.run(program), 2 seconds)

  // Создание таблицы user
  println("Создание таблицы БД")
  exec(User.users.schema.create)

  // Создание и вставка данных в БД
  println("\nВставка тест данных")
  exec(User.users ++= User.freshTestData)

  // Запуск текстового запроса и вывод результата:
  println("\nВыбор всех пользователей:")
  exec( User.users.result ) foreach { println }
//  //Результат
//  // [info] running runUser
//  Создание таблицы БД
//
//  Вставка тест данных
//
//  Выбор всех пользователей:
//    User(Alex,Dider,23,AlexDi@gmail.com,1)
//  User(Scott,Cawton,23,Cawton2@gmail.com,2)
//  User(Lev,Overko,19,lev@gmail.com,3)
//  [success] Total time: 7 s, completed 24 янв. 2022 г., 17:27:54

}
