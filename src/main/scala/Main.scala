import io.circe.generic.auto._
import me.ericjiang.aws.lambda.scalaruntime.LambdaRuntime

object Main extends App {
  println("Hello, World!")
  case class Request(name: String)
  case class Response(message: String)
  def handler(request: Request): Response = Response(s"Hello, ${request.name}")
  new LambdaRuntime().run(handler _)
}
