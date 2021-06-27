organization in ThisBuild := "com.pedrolge"
version in ThisBuild := "1.0"

lagomCassandraEnabled in ThisBuild := false
lagomKafkaEnabled in ThisBuild := false

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"


val pac4jVersion = "3.7.0"
val lagomPac4j = "org.pac4j" %% "lagom-pac4j" % "2.2.1"
val pac4jOidc = "org.pac4j" % "pac4j-oidc" % pac4jVersion
val pac4jHttp = "org.pac4j" % "pac4j-http" % pac4jVersion
val pac4jJwt = "org.pac4j" % "pac4j-jwt" % pac4jVersion
val pac4jSql = "org.pac4j" % "pac4j-sql" % pac4jVersion

val nimbusJoseJwt = "com.nimbusds" % "nimbus-jose-jwt" % "6.0"
val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.3" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.1.1" % Test
val pureConfig = "com.github.pureconfig" %% "pureconfig" % "0.16.0"
val scalaLogging = "com.typesafe.scala-logging" %% "scala-logging" % "3.9.3"

val slick = "com.typesafe.slick" %% "slick" % "3.3.3"
val slickHikari = "com.typesafe.slick" %% "slick-hikaricp" % "3.3.3"
val postgresDriver = "org.postgresql" % "postgresql" % "9.4-1206-jdbc42"
val hasher = "com.roundeights" %% "hasher" % "1.2.0"

lazy val `irplus-be` = (project in file("."))
  .aggregate(`experiment-api`, `experiment-impl`, `mlflow-api`)


lazy val `mlflow-api` = (project in file("external/mlflow/api"))
  .settings(
    name := "mlflow-api",
    libraryDependencies ++= Seq(
      lagomScaladslApi,
      lagomScaladslServer % Optional,
      lagomScaladslClient,
      pureConfig,
    )
  )

lazy val `experiment-api` = (project in file("experiment-api"))
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslApi
    )
  )
  .dependsOn(`mlflow-api`)

lazy val `experiment-impl` = (project in file("experiment-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      pac4jHttp,
      pac4jJwt,
      pac4jSql,
      lagomPac4j,
      nimbusJoseJwt,
      pac4jOidc,
      lagomScaladslTestKit,
      macwire,
      pureConfig,
      scalaTest,
      scalaLogging,
      slick,
      slickHikari,
      postgresDriver,
      hasher
    )
  )
  .settings(lagomForkedTestSettings)
  .dependsOn(`experiment-api`, `mlflow-api`)



lagomUnmanagedServices in ThisBuild += ("mlflow" -> "http://localhost:5000/api")