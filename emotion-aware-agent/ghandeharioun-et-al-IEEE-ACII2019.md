# EMMA: An Emotion-Aware Wellbeing Chatbot

## System Design

* experience sampling 
  * uses affective accompanying text
  * __samples user's mood and reponds to it appropriately__
  * __emotional state self-report__ (Russel's two-dimensional model of emotion)
    * ![UI](https://github.com/ypchen520/MyTrack/blob/master/emotion-aware-agent/img/UI.png)
    * two dimensions for mood: *valence* and *arousal*
      * **valence**: *Pleasant/Unpleasant*
      * **arousal**: *High/Low energy* 
* conversation
  * ![texts](https://github.com/ypchen520/MyTrack/blob/master/emotion-aware-agent/img/texts.png)
  * __content__
    * selected from the pool of scripted texts
    * rule-based decision tree
      * *group condition*
      * the user's most recently recognized affective state

## Result and Discussion

* extravert vs. introvert
  * extraverts liked the affective agent significantly more than introverts
* influence on mood
  * emotionally appropriate response :arrow_right: higher percentage of positive responses
* unnecessary exaggeration
  * subtle emotions should be reponded to with neutral response
* __behavior change application needs to support__ ```self-reflection```
  * intelligent systems could provide opportunities for the user to self-reflect __at the right pace and frequency__

## My Thoughts

* independent variables
  1. time-based notification vs. context-based notification
  2. (Likert Scale vs. Russel's two-dimensional model of emotion)
  3. neutral reply vs. emotion-aware reply
* What's different?
  * we ask the user to answer other survey questions in addition to mood
  * we wanted to see if this design increases the logging frequency
    * the paper showed that emotion-aware reply leads to more positive mood reports
    * prior work has shown that positive mood increases the user's response rate to notifications
* 4 conditions
  1. time-based notification + neutral reply
  2. time-based notification + __emotion-aware reply__
  3. __context-based notification__ + neutral reply
  4. __context-based notification__ + __emotion-aware reply__
