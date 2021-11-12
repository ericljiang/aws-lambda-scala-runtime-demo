Global / onChangedBuildSource := ReloadOnSourceChanges

lazy val distRuntime = taskKey[File]("Produces a zip archive containing the natively compile runtime")

lazy val root = project
  .in(file("."))
  .enablePlugins(NativeImagePlugin)
  .settings(
    name := "Demo of Scala runtime for AWS Lambda",
    scalaVersion := "2.13.6",

    resolvers += "jitpack" at "https://jitpack.io",
    libraryDependencies ++= Seq(
      "com.github.ericljiang" % "aws-lambda-scala-runtime" % "72f958b916"
    ),

    nativeImageOutput := file("target") / "native-image" / "runtime",
    nativeImageOptions ++= Seq(
      "-H:+ReportExceptionStackTraces",
      "-H:+TraceClassInitialization",
      "-H:+PrintClassInitialization",
      "--no-fallback",
      "--allow-incomplete-classpath",
      "--enable-http",
    ),

    distRuntime := {
      val image = nativeImage.value
      val output = file("target") / "aws-lambda-runtime" / "runtime.zip"
      IO.zip(Some((image, "bootstrap")), output, None)
      output
    }
  )
