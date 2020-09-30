# System Design
* experience sampling 
  * uses affective accompanying text
  * __samples user's mood and reponds to it appropriately__
  * __emotional state self-report__ (Russel's two-dimensional model of emotion)
    * ![UI](https://github.com/ypchen520/MyTrack/blob/master/emotion-aware-agent/img/UI.png)
    * __two dimensions for mood__: *valence* and *arousal*
    * *Pleasant/Unpleasant*
    * *High/Low energy* 
* conversation
  * ![texts](https://github.com/ypchen520/MyTrack/blob/master/emotion-aware-agent/img/texts.png)
  * __content__
    * selected from the pool of scripted texts
    * rule-based decision tree
      * *group condition*
      * the user's most recently recognized affective state

* Web-based UI
  * StudyPortal platform

# Result and Discussion
* extravert vs. introvert
* influence on mood
* __behavior change application needs to support Self-reflection__

# My thought
* independent variables
  1. time-based vs. context-based
  2. Likert Scale vs. Russel's two-dimensional model of emotion
  3. neutral vs. emotion-aware

1. time-based + Likert Scale vs. time-based + __Russel's + emotion-aware__
  * time-based notification
  * for logging mood, we implement a similar design based on the paper: __Russel's two-dimensional model of emotion__
    * based on the mood logged by the user
      * we select appropriate reponse
    * __rule-based decision tree__
    * __dialogflow__
  * measure
    * __mood__
    * __logging frequency__
  * *how do we phrase the responses?*
2. time-based + Likert Scale vs. __context-based__ + Likert Scale
3. context-based + Likert Scale vs. __context-based__ + __Russel's + emotion-aware__
