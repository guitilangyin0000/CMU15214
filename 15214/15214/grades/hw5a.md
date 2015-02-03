hw5a Feedback
============

#### Description of Framework (8/10)

* -2, You do not discuss the extension points provided by the framework interfaces for the data/analysis plugin. 


#### Framework Design (30/35)

* -1, Your design model doesn't show any way to register the plugin with the framework; your framework needs to keep track of which plugins are registered in it in order to interact with them.


* -1, When registering a data/analysis plugin to the framework, it is useful to inform the plugin that it has been registered so it can do any setup work. This can be done by adding an `onRegister()` method to the data/analysis plugin interface.


* -1, Your analyze method is missing important argument which makes framework unable to pass data to analysis plugin.

 * -2, The plug-ins have direct access to  all parts of the framework internals.


#### Presentation (30/30)

---

#### Total (68/75)

Late days used: 0 (2 left for hw5)

---

#### Additional Notes

Graded by: Siyu Wei (siyuwei@andrew.cmu.edu)

To view this file with formatting, visit the following page: https://github.com/CMU-15-214/yangwan2/blob/master/grades/hw5a.md
