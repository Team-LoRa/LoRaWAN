# Using the LoRaMessenge Configuration Service

Table of contents:

[How to Format an Input File](#Input-Files)  

[How an Encoding File Should Look](#Encoding-Files)  

[How a Decoding File Should Look](#Decoding-Files)  


# Input-Files  

The LoRaMessenger configuration service must be given a JSON file as input. The
format of this JSON file is described below.  

First create a dictionary where the keys are the names of the applications you
wish to use with LoRaMessenger. These keys should point to dictionaries which
have the following content:
- url : The address you would like you application to send data to.
- api : A dictionary where the keys are the names of the APIs you wish to use
        with LoRaMessenger. These keys should point to dictionaries which have
        the following contents:   
    * Parameter-name : A key which points to a list in one of the two following
                      formats:
        * If the parameter accepts an arbitrary integer or double, specify
          "int-param" or "double-param" at list index 0. At list index 1, specify
          the number of bytes you would like to dedicate to storing this
          parameter's value. Keep in mind that a single LoRaMessenger packet
          has only 10 bytes of payload space.
        * If the parameter has a concise list of valid inputs ( less than 256 )
          you can place all the values you would like to be able to pass for this
          parameter in a list.

Below is an example of an input file (this can also be found in the examples folder)
``` JSON
{
    "TempControl" : {
        "url" : "TempControl.com",
        "tempUp" : { "increaseAmount" : [ "int-param", "2" ], "room" : [ "Living Room", "Dining Room", "Kitchen", "Bedroom", "Garage"] },
        "tempDown" : { "decreaseAmount" : [ "int-param", "2" ], "room" : [ "Living Room", "Dining Room", "Kitchen", "Bedroom", "Garage"] }
    },
    "LightControl" : {
        "url" : "LightControl.com",
        "on" : { "intensity" : [ "int-param", "2" ], "room" : [ "Living Room", "Dining Room", "Kitchen", "Bedroom", "Garage"] },
        "off" : { "room" : [ "Living Room", "Dining Room", "Kitchen", "Bedroom", "Garage"] }
    }
}
```

# Encoding-Files
If everything went well, the encoding file should look like this:

``` JSON
{

  "TempControl" : {
    "byte_code" : "1",

    "tempUp" : {
      "byte_code" : "1",
      "params" : [
        {
          "name" : "increaseAmount",
          "values" : "int-param",
          "length" : "2"
        },
        {
          "name" : "room",
          "values" : {
            "Living Room" : "1",
            "Dining Room" : "2",
            "Kitchen" : "3",
            "Bedroom" : "4",
            "Garage" : "5"
          }
        }
      ]
    },

    "tempDown" : {
      "byte_code" : "2",
      "params" : [
        {
          "name" : "increaseAmount",
          "values" : "int-param",
          "length" : "2"
        },
        {
          "name" : "room",
          "values" : {
            "Living Room" : "1",
            "Dining Room" : "2",
            "Kitchen" : "3",
            "Bedroom" : "4",
            "Garage" : "5"
          }
        }
      ]
    }
  },

  "LightControl" : {
    "byte_code" : "2",

    "on" : {
      "byte_code" : "1",
      "params" : [
        {
          "name" : "intensity",
          "values" : "int-param",
          "length" : "2"
        },
        {
          "name" : "room",
          "values" : {
            "Living Room" : "1",
            "Dining Room" : "2",
            "Kitchen" : "3",
            "Bedroom" : "4",
            "Garage" : "5"
          }
        }
      ]
    },

    "off" : {
      "byte_code" : "2",
      "params" : {
        "name" : "room",
        "values" : {
          "Living Room" : "1",
          "Dining Room" : "2",
          "Kitchen" : "3",
          "Bedroom" : "4",
          "Garage" : "5"
        }
      }
    }
  }

}
```
This file should be placed in the Android assests folder


# Decoding-Files
If all went well then the decoding file should look like this:

``` JSON
{

  "1" : {
    "url" : "TempControl.com",

    "1" : {
      "name" : "tempUp",
      "params" : [
        {
          "name" : "increaseAmount",
          "values" : "int-param",
          "length" : "2"
        },
        {
          "name" : "room",
          "values" : {
            "1" : "Living Room",
            "2" : "Dining Room",
            "3" : "Kitchen",
            "4" : "Bedroom",
            "5" : "Garage"
          }
        }
      ]
    },

    "2" : {
      "name" : "tempDown",
      "params" : [
        {
          "name" : "increaseAmount",
          "values" : "int-param",
          "length" : "2"
        },
        {
          "name" : "room",
          "values" : {
            "1" : "Living Room",
            "2" : "Dining Room",
            "3" : "Kitchen",
            "4" : "Bedroom",
            "5" : "Garage"
          }
        }
      ]
    }

  },

  "2" : {
    "url" : "LightControl.com",

    "1" : {
      "name" : "on",
      "params" : [
        {
          "name" : "intensity",
          "values" : "int-param"
        },
        {
          "name" : "room",
          "values" : {
            "1" : "Living Room",
            "2" : "Dining Room",
            "3" : "Kitchen",
            "4" : "Bedroom",
            "5" : "Garage"
          }
        }
      ]
    },

    "2" : {
      "name" : "off",
      "params" : {
        "name" : "room",
        "values" : {
          "1" : "Living Room",
          "2" : "Dining Room",
          "3" : "Kitchen",
          "4" : "Bedroom",
          "5" : "Garage"
        }
      }
    }

  }

}
```
