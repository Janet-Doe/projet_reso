# S6 project RESO : 8-Ball
Bauvent Melvyn, Grenier Lilas  

> ![Image](https://www.svgrepo.com/show/485707/eightball.svg)

Inspired by the classic 8-Ball game (in which a toy shaped like a pool's eightball is asked a 
yes-or-no question and shaken, in order to give a random answer) this project has the goal to connect people 
by asking them to answer each other's question in a similar way.  

The client-server system we use gives the users the ability to anonymously ask questions, 
and to answer those of others through the typical 8-Ball answers, in a cross-exchange between strangers.

## How to make it work?

Don't worry, it's quite easy.

First step: clone this repository with your friends.  

Second step: one of the users run an instance of the Server class. 

Third step: while connected to the same network, each one can run a Client instance and connect to that newly 
open server, answering each others' questions anonymously (or as anonymous as it can be if only one or two people 
are connected on the server).

## Documentation
The Javadoc of the code 

## Conception

### Sequence diagram 
Here's the sequence diagram of the interactions between a lone Client and the Server, featuring Threads.
[![](https://mermaid.ink/img/pako:eNqtV02P2yAQ_SvIJ0dy-gN8WCnqXnqpVkpPVS4Uj2O0MaRAElWr_e8F4w8gmMTZcEkgM28eM8ODfGSEV5CVmYS_J2AEXineC9zuGNJjC-IMAq1fXtAPBmrLyTuoTSVAyhIxuFyt5rj7KNCRC7WyIKENWhs8C10i63AVTnZfrRt6xaojZac2sr_Wx-0jJpz94K7hjlnnA-dHVHOBAJMGkQMFpuwvAcWaCqnesBfETn2Gdi3_c6prEAWyn98OwPaqWU3I83A-Z8cuSiuZOQEE6BlyB8Sh8JMrQNwAWbwiDaYa6A0QvmCqJMKo1VXAexiSaQYmip6xRv4e5NLOO9I2zfGKRevtsE75dpnr45SeZZRGkPf76zhkatO3v529TYfAjFhVR25j4iK0Uv1smphVee8eLaZFuqOYNj8doKll1yQDMaS4U3C3vncdCc1zP4rE4q4fa7dx1GJhbFOML0Q27tG4qhGAqwWt63eKNgjaJAXoM3Mto9x-dQY9DTvJXadV5JRas5QoWAvv_BO9osA0jYlkQ3QiqhoqkTwCoTUlo5pO6DG1HVrOqbMZRywGZEkr8H80o9-tt_GKI8lb0DTYPukhTY1nuud-FXC7tHA6Z3Udez7g2tvBZDfP_3YfdiIxQUX4THW2qMWkTabQQ-Z7bQhUIbwmu45i1ago0Xo9Q3jzVWQrSaG1G9kweTEyluCUTuhwk7pwyawOKjwW1lHc6-vTxdApf_wkUEZ4q5v_eQ-VYSSR3SYuA9Mk30TWp6T7eA83c5h2VAvePqWfx2hp_XE80tmcuT0XlCLs_-AifYxReKc-zse_Xp_7NHN3XDjxlipH9HAuEo2Zp9oysQgk-Eor0LTiz-5-b5MDl-DWtYLZR_x9R_cGqPvmGNVueMfcfgH30FmRtSBaTCv9f_bDgOwynbcWdlmpv1ZYvO-yHfvUdvik-PYfI1mpxAmKTPDTvsnKGh-knp2OlWbV_xMeV4-Y_eZ8mH_-B61W3to?type=png)](https://mermaid.live/edit#pako:eNqtV02P2yAQ_SvIJ0dy-gN8WCnqXnqpVkpPVS4Uj2O0MaRAElWr_e8F4w8gmMTZcEkgM28eM8ODfGSEV5CVmYS_J2AEXineC9zuGNJjC-IMAq1fXtAPBmrLyTuoTSVAyhIxuFyt5rj7KNCRC7WyIKENWhs8C10i63AVTnZfrRt6xaojZac2sr_Wx-0jJpz94K7hjlnnA-dHVHOBAJMGkQMFpuwvAcWaCqnesBfETn2Gdi3_c6prEAWyn98OwPaqWU3I83A-Z8cuSiuZOQEE6BlyB8Sh8JMrQNwAWbwiDaYa6A0QvmCqJMKo1VXAexiSaQYmip6xRv4e5NLOO9I2zfGKRevtsE75dpnr45SeZZRGkPf76zhkatO3v529TYfAjFhVR25j4iK0Uv1smphVee8eLaZFuqOYNj8doKll1yQDMaS4U3C3vncdCc1zP4rE4q4fa7dx1GJhbFOML0Q27tG4qhGAqwWt63eKNgjaJAXoM3Mto9x-dQY9DTvJXadV5JRas5QoWAvv_BO9osA0jYlkQ3QiqhoqkTwCoTUlo5pO6DG1HVrOqbMZRywGZEkr8H80o9-tt_GKI8lb0DTYPukhTY1nuud-FXC7tHA6Z3Udez7g2tvBZDfP_3YfdiIxQUX4THW2qMWkTabQQ-Z7bQhUIbwmu45i1ago0Xo9Q3jzVWQrSaG1G9kweTEyluCUTuhwk7pwyawOKjwW1lHc6-vTxdApf_wkUEZ4q5v_eQ-VYSSR3SYuA9Mk30TWp6T7eA83c5h2VAvePqWfx2hp_XE80tmcuT0XlCLs_-AifYxReKc-zse_Xp_7NHN3XDjxlipH9HAuEo2Zp9oysQgk-Eor0LTiz-5-b5MDl-DWtYLZR_x9R_cGqPvmGNVueMfcfgH30FmRtSBaTCv9f_bDgOwynbcWdlmpv1ZYvO-yHfvUdvik-PYfI1mpxAmKTPDTvsnKGh-knp2OlWbV_xMeV4-Y_eZ8mH_-B61W3to)

