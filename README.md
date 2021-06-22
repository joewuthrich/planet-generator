# Planet Generator
A way to generate random planets in minecraft, with a number of different settings.

## Planet Generation
The planets are generated in the following way:
- Get parameters: [radius] [preset]
- Generate the sphere, and remove caves according to simplex noise
- Texture the sphere according to parameters

![2021-06-17_19 24 40](https://user-images.githubusercontent.com/62530039/122360108-3f667000-cfaa-11eb-8b67-71cb028e4504.png)

 
 

## Planet Presets
Planets can also be generated from a config of presets, which have block values and their respective percentage chance to be selected for that parameter. 

The config can also store subclasses, so you can group presets together under one name.

https://user-images.githubusercontent.com/62530039/122359847-07f7c380-cfaa-11eb-9f17-c9ecb2091747.mp4

