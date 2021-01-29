GRAVITY FLUX CUSTOM LEVEL GUIDE 
============================

### Kevin Kinney 

---

## Basics


Each Level is stored in an xml file. Use UTF-8 as encoding. The first line should look like this: 
```
<?xml version="1.0" encoding="UTF-8"?> 
```

Make sure all custom levels are stored in a file called `custom.xml` in the same folder as the `.jar` file 

---

## Levels


All levels are enclosed within the `<levels></levels>` tags. Each level being with a `<level></level>` tag. This tag has a required attribute, width, that indicates the width of the level. E.g. `<level width=”1000”></level> `

The screen is 1000 wide, and about 700 tall with the display taking up the first 50 or so of that. This coordinate system will work regardless of how big your screen is. 

---

## SpaceMan


The next step is to add a SpaceMan tag, which looks like `<spaceman></spaceman>`. The SpaceMan contains tags for x position (e.g. `<xpos>10</xpos>`) and y position (e.g. `<ypos>10</ypos>`), referring to the bottom left point of the SpaceMan object. 

---

## Flag


Next, the level needs a Flag. Add this with a `<flag></flag>` tag. A flag tag has the same xpos and ypos requirements as a SpaceMan. 

---

## Hazards


Next, add hazards (spikes) to the level enclosed within a `<hazards></hazards>` tag. Each hazard is a `<hazard></hazard>` tag with a type attribute. The options for type are either spike or spike-flipped. The latter orients the spikes facing tip downward and uses the top left coordinate instead of the top right. Within the `<hazard></hazard>` tag, both `<xpos></xpos>` and `<ypos></ypos>` are needed, as well as a `<width></width>` tag specifying how many spikes wide the hazard is. Each Spike is 25 units wide.  

---

## Platforms


Finally, add platforms within the <platforms></platforms> tag. Each platform is its own <platform></platform> tag and has <xpos></xpos> and <ypos></ypos> (Top Left Corner) as well as <width></width> and <height></height>. 

Custom levels are run in the order they appear in the xml and can be played by selecting custom from the start menu. Have fun!  

 