# Team20

All progress reports for Team20 should be updated with the link below:

https://docs.google.com/document/d/1prMKFoJ215ErJ2KCtvMhDXFKkg-5J1QH820hTNTQ0KU/edit?usp=sharing

## Project Part2 Feedback

> Object Oriented Analysis
    >> Why ingredient has no collaborator, Are ingredients not stored in some class. 
    >>What you mean by food in responsibilities of storage, there is no class named food, I think you meant ingredients. Why recipe and storage are named multiple times in collaborators one time is enough (same for other crc cards). 
    >>Mealplan should not have a responsibility of changing the servings. The responsibility “knows the number of servings” should be “knows the number of servings of each recipe or item in the meal plan”. Why shoppinglist contains the list of ingredients in storage and list of ingredients needed for meal plan, it should only contain the list of ingredients required to buy. Fragments and activities are UI classes, you can also say that they are controllers, they should not have responsibilities of business logic, they only display things or take things as input, they don’t sort, store, add, edit, delete things.
    >> Cohesive classes: some classes interact with many other classes, this should not be the case, first remove your business logic from ui, controller logic, reduce the cohesion between classes
    >> Coupling managed: no, only one module based design, divide your classes into multiple modules
> Product Backlog
 >> ✘ Requirements are not numbered


 ##Project Part 3 Rubric

 Addressing Feedback
•    All well addressed
•    Issues tracked

Code Base of Prototype
•    Excellent quality
•    At least ½ of requirements implemented and fully done
•    Comprehensive connectivity to server
•    Clear and readable code with useful comments
•    Follows conventions
•    Checks for errors

Code Documentation
•    Third party could easily understand
•    Complete and correct Javadoc comments for entity (model) classes and methods
•    Consistent with requirements, design, code, and tests

Test Cases
•    Test exist and can run
•    Convincing and passing testing for completed implementation
•    Comprehensive unit tests of entity (model) classes
•    Comprehensive intent tests for implemented user stories
•    Correct tests
•    Realistic test data
•    Consistent with requirements, design, code, and documentation

Object-Oriented Design
•    Proper OO design with clear design intent
•    Separation of concerns and/or layering
•    Encapsulation and information hiding
•    Clear classes and interfaces
•    Key elements described
•    Correct notation
•    Neatly laid out and labeled diagrams
•    Helpful explanations or commentary
•    Consistent with requirements, code, tests, and documentation

Product Backlog (Updated)
•    Numbered, tracked, and organized requirements
•    Comprehensive requirements
•    Complete story point estimates
•    Complete risk levels
•    Displays understanding of requirements specification/elicitation
•    Requirements noted for half-way checkpoint

User Interface Mockup and Storyboard (Updated)
•    Consistent and clear
•    Complete UI mockups
•    Labeled elements on UI mockups
•    Detailed storyboard
•    Labeled actions for storyboard transitions
•    Covers all requirements
•    Intuitive user interface
•    Displays understanding of UI mockups and storyboarding

Sprint Planning and Review
•    Comprehensive
•    Displays understanding of Scrum
•    Displays regular and frequent pacing of working software
•    Each sprint is planned by user story
•    Riskier requirements are done earlier
•    Each sprint is reviewed
•    Members all present at each review
•    User stories are fully “done done” (implemented, tested, integrated, documented)
•    Early and frequent integration
•    Continuous integration actions

Tangible Demo
•    Demo ability
•    Clear and coherent
•    Logically organized by tangible features for user
•    Realistic data and inputs

Tool Practices
•    All team members contribute to GitHub regularly
•    Effective use of GitHub features

Relative Quality
•    Well above average relative quality, overall comprehensiveness, creativity, attractiveness, and innovation
