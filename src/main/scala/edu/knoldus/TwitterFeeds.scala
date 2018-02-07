package edu.knoldus

import java.time.LocalDate
import java.time.format.DateTimeFormatter

import edu.knoldus.Application.configurationBuilder
import twitter4j.{Query, TwitterFactory}

import scala.collection.JavaConverters._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TweeterFeeds {
  val count = 50

  val twitter = new TwitterFactory(configurationBuilder.build).getInstance


  def getTweets(input: String): Future[List[twitter4j.Status]] = Future {
    try {
      val query = new Query(input)
      query.setSince("2018-01-01")
      query.setCount(count)
      val resultList = twitter.search(query)
      resultList.getTweets.asScala.toList
    }
    catch {
      case exception: Exception => throw exception
    }
  }

  def getTweetsCount(input: String): Future[Int] = Future {
    try {
      val query = new Query(input)
      query.setSince("2018-01-01")
      query.setCount(count)
      val resultList = twitter.search(query)
      resultList.getTweets.size
    }
    catch {
      case exception: Exception => throw exception
    }
  }

  def getAverageTweets(input: String, startDate: String, endDate: String): Future[Long] = Future {
    try {
      val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
      val startDate = "2018-01-01"
      val endDate = " 2018-02-01"
      val oldDate = LocalDate.parse(startDate, formatter)
      val newDate = LocalDate.parse(endDate, formatter)
      val numberOfDays = newDate.toEpochDay() - oldDate.toEpochDay()
      val query = new Query(input)
      query.setCount(count)
      query.setSince(startDate)
      query.setUntil(endDate)
      twitter.search(query).getTweets.size() / numberOfDays
    }
    catch {
      case exception: Exception => throw exception
    }
  }

  def getLikes(input: String): Future[Int] = Future {
    try {
      val query = new Query(input)
      query.setSince("2018-01-01")
      query.setCount(count)
      val resultList = twitter.search(query)
      val tweets = resultList.getTweets.asScala.toList
      val likesCount = tweets.map(_.getFavoriteCount)
      likesCount.size / tweets.size
    }
    catch {
      case exception: Exception => throw exception
    }
  }

  def getRetweets(input: String): Future[Int] = Future {
    try {
      val query = new Query(input)
      query.setSince("2018-01-01")
      query.setCount(count)
      val resultList = twitter.search(query)
      val tweets = resultList.getTweets.asScala.toList
      val retweetCount = tweets.map(_.getRetweetCount)
      retweetCount.size / tweets.size
    }
    catch {
      case exception: Exception => throw exception
    }
  }
}


