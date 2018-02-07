package edu.knoldus

import com.typesafe.config.ConfigFactory
import org.apache.log4j.Logger
import twitter4j.conf.ConfigurationBuilder

import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object Application extends App {

  val log = Logger.getLogger(this.getClass)

  val config = ConfigFactory.load("application.conf")
  val consumerKey = config.getString("TwitterValidation.Key.consumerKey")
  val consumerSecret = config.getString("TwitterValidation.Key.consumerSecret")
  val accessToken = config.getString("TwitterValidation.Key.accessToken")
  val accessTokenSecret = config.getString("TwitterValidation.Key.accessTokenSecret")

  val configurationBuilder = new ConfigurationBuilder
  configurationBuilder.setDebugEnabled(true)
    .setOAuthConsumerKey(consumerKey)
    .setOAuthConsumerSecret(consumerSecret)
    .setOAuthAccessToken(accessToken)
    .setOAuthAccessTokenSecret(accessTokenSecret)
  val caller = new TweeterFeeds
  val tag = "#Nancy"
  val TweetsRecord = caller.getTweets(tag)
  TweetsRecord onComplete {
    case Success(value) => log.info("\n\n Tweets are :" + value)
    case Failure(value) => log.info(value.getMessage)

      Thread.sleep(500)
  }

  val NoOfTweets = caller.getTweetsCount(tag)
  NoOfTweets onComplete {
    case Success(value) => log.info("\n\nTweets Count is : " + value)
    case Failure(value) => log.info(value.getMessage)
  }

  val AverageTweets = caller.getAverageTweets(tag, "2018-01-01", "2018-02-05")
  AverageTweets onComplete {
    case Success(value) => log.info("\n\nAverage number of tweets are :" + value)
    case Failure(value) => log.info(value.getMessage)
  }

  val LikesPerTweet = caller.getLikes(tag)
  LikesPerTweet onComplete {
    case Success(value) => log.info("\n\nlikes per Tweets are:" + value)
    case Failure(value) => log.info(value.getMessage)
  }

  val ReTweets = caller.getRetweets(tag)
  ReTweets onComplete {
    case Success(value) => log.info("\n\nRetweets are:" + value)
    case Failure(value) => log.info(value.getMessage)
  }

}


