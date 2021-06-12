# DungeonGenerator
A way to generate random rooms and passageways similar to a roguelike game, and then place those passageways and rooms into Minecraft.


## Room Generation
The rooms and passageways are generated in the following way:
- Get parameters: [number of rooms], [x coordinate center], [z coordinate center], [minimum room edge length], [maximum room edge length]
- Generate [number of rooms] rooms in side a circle
- Space the rooms out until they no longer touch
- Connect each room with a complete graph
- Use a version of Kruskal’s MST Algorithm to generate the optimum connections for the rooms
- Reintroduce a few edges from the complete graph to create cycles


https://user-images.githubusercontent.com/62530039/121766328-1e1a1400-cba5-11eb-891b-9f1127919560.mp4


