# Planet Generator
A way to generate random planets in minecraft, with a number of different settings.

## Planet Generation
The planets are generated in the following way:
- Get parameters: [radius] [overlay] [underlay] [comma,seperated,material,list] [cave] [biome] [mixed/gradient/blob]
- Generate the sphere, and remove caves according to simplex noise
- Texture the sphere according to parameters

![2021-06-14_21 30 21](https://user-images.githubusercontent.com/62530039/121871224-fb7c2c80-cd57-11eb-9699-8f97c53aa5e4.png)
 
 
 
## Planet Presets
Planets can also be generated from a config of presets, which have block values and their respective percentage chance to be selected for that parameter. 

The config can also store subclasses, so you can group presets together under one name.

https://user-images.githubusercontent.com/62530039/121898765-d7c8de80-cd77-11eb-9c68-f16e098a2dac.mp4
