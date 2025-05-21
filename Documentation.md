# Documentation

# Partially/Fully implemented

## Magical Minecraft

Impure End Cristal Block:
    TODO: spawn in end dimension

Impure End Cristal:
    drop from Impure End Cristal Block, need at least diamond pickaxe
    More drops with fortune

End Cristal:
    craft: merger: orium orb + impure_end_cristal + popped_chorus_fruit
    Get 9 from End Cristal Block

End Cristal Block:
    Craft it from 9 End Cristal


Magical craft table: New Menu and Screen
    -> To craft magical related things
    new recipes, serializer and input type:
        magic_shapeless
        magic_shaped (does not work for symetric recipe)
    Craft:
        G: soul_torch, L:lapis_block, O:obsidian, S:soulsand, D:diamond
        GDG
        SLS
        OOO


Magic Cristal:
    Craftable with Magical crafting table and normal craftable
        D:diamond, C:end cristal, E:emerald, L:Lapis
        DCD
        CEC
        LCL
    actually shapeless


Magic Orb: 
    TODO: Craft: merger: orium_orb + choarcoal + blaze_powder

Invocation Orb?:
    TODO: Craftable with Magical Craft table
        Magic Orb,...


Magic Stick:
    Craftable with Magical craft table
        M:Magic Cristal, /:end_wood stick
            /
          M
        /


Lighting staff:
    Consume 1 magic orb to summon a lightning bolt
    -> Lightning particle
    TODO: enchantment
    TODO: craftable with magic craft table
        /: Magic stick, T:Lightning Rod, o:special item?



## Better Minecraft

### New crafting recipe

New lead craft only based on strings:
    S:string
     SS
     SS
    S

Name Tag: shapeless
    P:paperx2, S:string

TODO: craft saddle
    W:#minecraft:wool, L:minecraft:leather, T:minecraft:tripwire_hook
    "LWL"
    " L "
    " T "


### Copper material

Copper tools (sword, pickaxe, axe, hoe, shovel)

Copper armor: helmet, chestplate, leggings, boots, horse_armor
    TODO: trim -> not visible in item form yet


### Pure Quartz material

Between diamond and netherite, upgrade diamond to pure quartz

Modify netherite: craftable form pure_craft armor and health +4 (2 hearts)
Netherite craftable from pure quartz tools (not diamond anymore)
Change netherite_upgrade_smithing_template:
        recipe: replaced netherrack by neetherbrick
        texture


Pure quartz
    Craft: in merger: orium_orb + ghast_tear + quartz

Pure Quartz Upgrade (pure_quartz_upgrade_smithing_template) 
    Find in nether forteress chest (modify loot_table/chests/nether_bridges)
    craft(x2): U:pure_quartz_upgrade_smithing_template, N:netherrack, I:iron_igot
    IUI
    INI
    III


Armor: helmet, chestplate, leggings, boots, horse_armor
    Give haste and underwater haste
    craft: smithing table: from diamond, Pure Quartz upgrade, pure Quartz
    TODO: trim -> not visible in item form yet

Tools: All
    craft: smithing table: from diamond, Pure Quartz upgrade, pure Quartz

    



### New weapons

#### halberd

Halberd: better range, slower attack, high damage
    TODO: custom entity_range_interaction enchantment?
    TODO: superior end




### New effect

Gigantism: makes you taller, faster, stronger
    Craftable with red mushroom
    Gigantism II from Gigantism I and glowstone
    Gigantism III from Gigantism II and glowstone
    Gigantism IV from Gigantism II and glowstone
    TODO: Throwable potion craft
    
Miniaturism: makes you smaller, faster, 
    Craftable with brown mushroom
    Miniaturism II from Miniaturism I and glowstone
    Miniaturism III from Miniaturism II and glowstone
    Miniaturism IV from Miniaturism III and glowstone 


TODO: Ressurection


### Redstone
TntV2:
    Craft: T:tnt, O:orum_orb, G:Gun_powder
        GOG
        OTO
        GOG

tntV3:
    craft: T:tnt_v2, O:orum_orb, G:Gun_powder
        GOG
        OTO
        GOG


TODO: v4?, v5?, fireTnt, ultra powerfull

TODO: superior_tnt:
    TODO: Craft: merger: superior orium orb + tnt v3? + fire_charge


### Orium

Orium: between redstone and magic

Orium particle

Orium Orb: Item

TODO: Orium spirit: (looks like allays in yellow)
    TODO: Can pick up in with leap


Orium torch: torch item 
    craft: redstone torch + electricity orb on top
    click to change state (switch off/on)
    emit light when on
    TODO: orium_wall_torch 

TODO: Orium generator: produce orium orb
    TODO: use Orium spirit around OR craftable with orium spirit (collectable with special item)

Merger: merge tow item together
    Requires Orium orb to work
    craft:
        /:Orium_torch, F:furnace, C:cobblestone, E:electricity_orb
        / /
        RFR
        CCC
    Recipe used in merger works if top and bottom are inverted
    New custom Menu and Screen
    New custom recipe and serializer
    TODO: custom state when using superior orium orb (purple)


TODO: superior orium orb (purple) for harder craft
    TODO: craft: merger: orium orb + end_core + redstone



### Other
Twiked mob generation to add randomness (scale, movement_speed, attack_damage)
TODO: mob spawning by command get affected (FinalizeSpawnEvent us not called)

armor_stand spawn with arms

TODO general:
    - advancement
    - give craft recipe
    - tool tips



# Idea

## Better Minecraft

### End

end material (armor and tools) made of pure_end_cristal + ?<rare> in merger ?:
    end upgrade smithing template : pure_end_cristal,  end smith template, end_stone

End trees (need at least netherite tools) ?

baby ender dragon: pet (-> middle_aged ender dragon -> old ender dragon ?)


### visual
New armor skin: 4 different can be crafted via new table
invisible item frame
more mobs head
new armor trim


### weapons
- spear: sthrowable, mid-long range, slow
- knifes/daggers: short range, very fast
- roped kunai: trhowable knife, comes back

sarbacane, darts
new bow ?
new arrow (dynamit arrow)


### Mobs
new animals: ants, lion, rhinoceros, elephant, lizard, butterfly, snake, shark, whale, small spider
new mobs: ghost (make you fly), superior end defender (drop end_core, really toff, explosion/lazers?)


### Nature
Carnivor flower:
    - Rafllesia: attire mobs ?

mob:
- Venus flytrap: attack near by mobs


### Redstone
pipes, entering/closing pipes
block breaker?
Touret (tourelle, shoot mob) 
Orium (magic) touret


### Food
Fried eggs
new plant & food: rice, corn, leek, pepper, avocado
cooker machine -> make lunch: really high sasieté
    craft: S:smoker, C:cobblestone, R:redstone_block, I:iron_igot, _:iron_pressure_plate
         _ 
        ISR
        CCC
    crafts:
        hambuger: bread, beef, tomato, milk
        sushi/maki: rice, salmon, avocado, kelp
        tacos: corn, pepper, avocado, chicken
     noodles: egg, wheat, pork, leak


### Other
more tnt
throwable dynamit
throwable rock
new elytra
farmable wood -> greenhouse: put a tree pousse + bones_meal to get wood block in some time (tree pousse not consume)
    Craft: D:dirt, G:glass_block, W:water_bucket, O:observer, C:composter
        GGG
        GWG
        DOC

glass helmet (breath under water):
    Craft: G:glass_panel
        GGG
        G G
        GGG
scaphandre helmet: breath under water even more:
    Craft: G:glass_helmet, C:copper_ingot
        CCC
        CGC
        CCC



## Magical Minecraft

staff: 
    - fire
    - fire explosing ball
    - water: knockbat water
    - ice: freeze entity, transforme blocks into ice
    - sand: throw sand block
    - dash
    - very high jump

spawn staff (different skin for mobs): skeletton, slime, blaze, ghast, zombie mob attack specific pointed target?

every staff need a special item that can be found in structures

new weapon: consume mana orbs
    - dark hoe: apply wither effect to all living entity, big slash (Right click) Faucher/Moira dash 
    - fire rope kunai: trhow fire on its way (Right click) burn every living entity near by
    - electicity daggers: incredible attack speed (Right click) slowdown every entity near by
    - trident: incredible knockback (Right click) 

new armor:
    No fallDamage
    - necromancian: Really tought
    - fire: can fly, fire resistance
    - electricity: speed
    - water: increadible speed in water, can fly when it rains




## Mutant

mutation potion
vanilla mutant animal: cow, chicken, sheep, bee
Better Minecraft mutant animal: shark, lizard
mutant mob: zombie, squeletton, enderman, spider, creeper, ghast

