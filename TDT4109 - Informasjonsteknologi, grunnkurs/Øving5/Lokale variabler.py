#!/usr/bin/env python
# coding: utf-8

# <nav class="navbar navbar-default">
#   <div class="container-fluid">
#     <div class="navbar-header">
#       <a class="navbar-brand" href="_Oving5.ipynb">Øving 5</a>
#     </div>
#     <ul class="nav navbar-nav">
#     <li><a href="Grunnleggende%20om%20funksjoner.ipynb">Grunnleggende om funksjoner</a></li>
#     <li><a href="Varierte%20funksjoner.ipynb">Varierte funksjoner</a></li>
#     <li class="active"><a href="Lokale%20variabler.ipynb">Lokale variabler</a></li>
#     <li><a href="Globale%20variabler.ipynb">Globale variabler</a></li>
#     <li><a href="Euklids%20algoritme.ipynb">Euklids algoritme</a></li>
#     <li><a href="Primtall.ipynb">Primtall</a></li>
#     <li><a href="Multiplikasjon.ipynb">Multiplikasjon</a></li>
#         <li><a href="Den%20store%20sporreundersokelsen.ipynb">Den store spørreundersøkelsen</a></li>
#     <li><a href="Arbeidsdager.ipynb">Arbeidsdager</a></li>
#     <li><a href="Sekantmetoden.ipynb">Sekantmetoden</a></li>
#     <li><a href="Not%20quite%20Blackjack.ipynb">Not quite Blackjack</a></li>
#         <li><a href="Funksjoner%20og%20Jupyter%20widgets.ipynb">Funksjoner og Jupyter widgets</a></li>
#     </ul>
#   </div>
# </nav>
# 
# # Lokale variabler
# 
# **Læringsmål:**
# - Kodeforståelse
# - Funksjoner
# 
# **Starting Out with Python:**
# - Kap. 5.4

# ## Generelt om lokale variabler
# Dette er ikke en del av øvingen, men kan være lurt å lese før du går videre.

# En lokal variabel er en variabel som blir lagd inne i en funksjons kodeblokk, og som ikke kan benyttes av kall utenfor funksjonen. Koden under viser et eksempel på dette. Prøv å kjøre koden og se hva som skjer. Hva tror du er galt?

# In[ ]:


def getName():
    name = input("Hva heter du? ")  

getName()
print("Hei,",name)


# Her er `name` en lokal variabel for funksjonen `getName()`. Dette medfører at koden i linje 5 ikke kan aksessere variabelen `name` slik den prøver, og at programmet krasjer. Vi får her feilmeldingen "NameError: name 'name' is not defined", som rett og slett betyr at programmet ikke har kjennskap til noen variabel eller funksjon ved navnet 'name'.
# 
# For å få koden til å fungere slik vi ønsker kan vi returnere name-variabelen og tilordne denne til en egen variabel - som kan kalles name eller noe helt annet - som linje 5 har tilgang til. Koden kan da se ut som under. Test gjerne koden og se at den virker.

# In[ ]:


def getName():
    name = input("Hva heter du? ")
    return name
  
name = getName()
print("Hei,",name)


# Koder kan også inneholde parametere som også fungerer som lokale variabler. Eksempelvis.

# In[ ]:


def printName(name):
    print("Hei ", name)
  
 
printName("Ola")


# Her vil "Ola" bli til **name**-variabelen og kun være tilgjengelig innenfor funksjonen.

# ## a)

# Alle kodesnuttene under inneholder en feil, men en av dem vil fortsatt kjøre uten å krasje; hvilken og hvorfor? **Prøv å finne feilen i hver kodesnutt.**

# In[ ]:


#Kodesnutt 1:
def cupcakes():
    cupcakes = 24
    print("Jeg har laget",cupcakes,"cupcakes")

def cakes():
    print("Men jeg har bare bakt",cake,"kake")
    cake = 1

cupcakes()
cakes()


# In[ ]:


#Kodesnutt 2:
def cupcakes():
    cupcake = 1
    print("Jeg har laget",cupcake,"cupcake")

def cakes():
    print("Og jeg har bakt",cupcake,"kake")

cupcakes()
cakes()


# In[ ]:


#Kodesnutt 3:
def cupcakes():
    cupcakes = 24
    print("Jeg har laget",cupcakes,"cupcakes")

def cakes():
    print("Men jeg har bare bakt",cake,"kake")

cupcakes()


# ***Dobbelklikk på teksten under og skriv svaret ditt i boksen som kommer opp***

# Kodesnutt 1: Sidene python leser språket oventifra og ned, så vil print under cake-funksjonen kjøres før cake har blitt definert.
# Kodesnutt 2: Her er cupcake variableen kun definert i funksjonen cupkakes, og python vil dermed ikke finne fram til variabelen når den blir kalt på i funksjonen cakes.
# Kodesnutt 3: Her er ikke cake definert i funksjonen cakes, men siden at funksjonen heller ikke blir kalt på, så vil det ikke komme opp noen error, da den bare vil unngå å kjøre hele definisjonen. 

# #### Hint

# Feilen i hver kode befinner seg i cakes()-funksjonen. Tenk på plassering av variabelen brukt i cakes-funksjonen.
# 
# For å finne ut hvilken kodesnutt som ikke vil krasje ved kjøring av koden, tenk på funksjonskall. Er det et tilfelle der koden med feil i ikke vil bli utført?

# ## b)

# I denne oppgaven skal du skrive to funksjoner. Den første skal ta inn to argumenter `x` og `y`, lage en variabel `num` = `x//y`, og returnere resultatet slik at programmet kan skrive til skjerm "Heltallsdivisjon av `x` over `y` gir `num`".
# 
# Den andre funksjonen skal ta inn ett argument x, lage en varibel num = x\*\*2, og returnere resultatet slik at programmet kan skrive til skjerm "Kvadratet av `x` er `num`".
# 
# ***Skriv koden din i kodeblokken under***

# In[29]:


def heltallsdivisjon():
    num = x1 // y
    return num 
x1 = int(input("Skriv et tall, x: "))
y = int(input("Skriv et tall, y: "))

def kvadrat():
    num = x2**2
    return num
x2 = int(input("Skriv et tall, x: "))

print("Heltallsdivisjon av ", x1, "over ", y, " gir ", heltallsdivisjon())
print("Kvadratet av ", x2, " er ", kvadrat() )


# Eksempel på kjøring:
# ```python
# Heltallsdivisjon av 23 over 4 gir 5
# Kvadratet av 3 er 9
# ```

# ## c)

# Begge funksjonene i b) har en variabel kalt num. Kan dette føre til noen problemer i koden? Hvorfor/Hvorfor ikke?
# 
# ***Dobbelklikk på teksten under og skriv svaret ditt i boksen som kommer opp***

# Dette vil ikke føre til noen direkte problemer, da de er lokale variabler, som hører til hver sin funksjon. De kan ikke benyttes av kall utenfor funksjonen. 
