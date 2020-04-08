Notest for development

1. Points, considered for the idea to create an app
- I can form my wordset and vocab for any language
- I can perform any exercises for my vocad
- Words in vocab can be changed in several statuses

LITERAL
 - ID int pk
 - USER_ID int required fk
 - TYPE word/phrase enum required
 - PERCEPTION_COEF int required default 0 (0 - 100)
 - LABEL_ID int fk

USER
 - NAME
 - ID
 
EXERCISE
 - ID
 - DESCRIPTION
 - VERSION
 - TEMPLATE
 
DAILY_PLAN
 - ID
 - USER_ID
 - EXERCISES_NUMBER
 - WORDS_TO_LEARN
 
LABEL
 - ID
 - NAME
 - USER_ID