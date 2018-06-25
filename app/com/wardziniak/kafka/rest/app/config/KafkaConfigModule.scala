package com.wardziniak.kafka.rest.app.config

import com.google.inject.{AbstractModule, Provides, Singleton}
import com.typesafe.config.Config
import com.typesafe.scalalogging.LazyLogging
import play.Environment
import pureconfig.loadConfig

class KafkaConfigModule (environment: Environment, untypedConfig: Config) extends AbstractModule with LazyLogging {

  @Provides
  @Singleton
  def provideAppConfig(): KafkaConfig = {
    // Config from constructor is used, instead of reading from file, to keep config overrides in tests
    loadConfig[KafkaConfig](untypedConfig, KafkaConfig.KeyName) match {
      case Right(config) => config
      case Left(error) =>
        logger.error("Config file parsing failed")
        //just let it fail, if the configuration is invalid there is no point in handling it any other way
        throw new RuntimeException(error.toList.mkString)
    }
  }

  override def configure(): Unit = {}
}
