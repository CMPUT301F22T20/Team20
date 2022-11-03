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