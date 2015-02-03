hw6 README
==========

This file provides some additional information about how we expect your map/reduce framework to run
and how the framework's initial setup can be configured.

---

## The Starter Code

Before beginning the assignment, we recommend looking through the starter code we have provided.
Begin by understanding the three classes below:

 * `MasterServer` - Run this file to start the master server. You will need to finish implementing
   this class.

 * `WorkerServer` - Run this file to start a worker server. You will need to finish implementing
   this class.

 * `AbstractClient`, `WordCountClient` & `WordPrefixClient` - Once you have implemented the
   `AbstractClient#execute()` method, you can run the latter two classes to submit a word count or
   word prefix task to the framework, respectively.

You should also take a look at the following important utility classes:

 * `Partition` - Represents a collection of files that a worker stores locally on its disk.

 * `WorkerInfo` - Represents a distinct worker server in the system. A list of `WorkerInfo` objects
   will be parsed from either the command line or system property files (discussed below), and will
   be passed as an an argument to the `MasterServer` constructor when it is first initialized.

 * `WorkerStorage` - Provides quick-and-easy access to a worker's local data storage.

---

## The Directory Structure

In this section, we discuss how and where workers should store their data and discuss the project's
initial setup and structure. The project's initial directory structure is given below:

```
worker_storage/
    - worker1/
        - data/
            - 1, 4, 5, 7, 9
        - intermediate_results/
        - final_results/
    - worker2/
        - data/
            - 1, 3, 6, 8, 9
        - intermediate_results/
        - final_results/
    - worker3/
        - data/
            - 2, 3, 6, 8, 10
        - intermediate_results/
        - final_results/
    - worker4/
        - data/
            - 2, 4, 5, 7, 10
        - intermediate_results/
        - final_results/
```

The `worker_storage` directory stores each worker's data in single sub-directory (each named after
the worker itself). We have further organized each worker's local storage into three distinct
sub-directories, each of which are discussed below:

 * A map worker has its partitions stored in its `../../data/` directory. Note that the starter code
   we have provided organizes partitions across workers such that every partition of data is stored
   by at least two worker servers. By replicating partitions across workers in this way, the
   framework is able to be more robust to worker server failures. For example, if the master server
   needs to map a task across a map worker that contains partition #1 and worker #1 suddenly fails,
   the master server could still potentially recover (since worker #2 also stores a copy of the data
   associated with partition #1).

 * A worker can use its `../../intermediate_results/` directory is to store intermediate key/value
   pair results generated during the map phase.

 * A worker can use its `../../final_results/` directory is to store final key/value
   pair results generated during the reduce phase.

---

## Running/testing your framework

Your framework should be implemented so that the client, master, and worker servers can be run
independently on different machines. You are encouraged to configure your framework however you like
(i.e. altering the number of workers, modifying which workers store which partitions, etc.). The
starter code does all of the work of parsing host/port/partition information into `WorkerInfo`
objects for you, so configuring your framework in these ways should be relatively simple:

 * *Modifying the `master.properties` & `workers.properties` files.* For your convenience, you may
   quickly configure your framework by modifying the `master.properties` and `workers.properties`
   files. We have provided an example configuration to start with.

 * *Specifying command line arguments.* This may be preferable when testing your framework on
   multiple machines, for example. We will also post some scripts on Piazza that will make it easy
   to simulate the process of running many different worker server instances on a single machine, so
   stay tuned!
