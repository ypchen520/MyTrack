# System Design
* experience sampling 
  * uses affective accompanying text
  * __samples user's mood and reponds to it appropriately__
  * __emotional state self-report__ (Russel's two-dimensional model of emotion)
    * __two dimensions for mood__: *valence* and *arousal*
    * *Pleasant/Unpleasant*
    * *High/Low energy* 
* conversation
  * bubbles
  * __content__
    * selected from the pool of scripted texts
    * rule-based decision tree
      * *group condition*
      * the user's most recently recognized affective state

* Web-based UI
  * StudyPortal platform

# Result
* extravert vs. introvert
* influence on mood
* __behavior change application needs to support Self-reflection__

# My thought
* 1st: 
  * time-based notification, 
  * for logging mood, we implement a similar design based on the paper: __Russel's two-dimensional model of emotion
    * based on the mood logged by the user
      * we select appropriate reponse
    * __rule-based decision tree__
    * __dialogflow__
  * *how do we phrase the responses?*
