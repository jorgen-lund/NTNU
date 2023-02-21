#!/usr/bin/env python
# coding: utf-8

# <nav class="navbar navbar-default">
#   <div class="container-fluid">
#     <div class="navbar-header">
#       <a class="navbar-brand" href="_Oving2.ipynb">Øving 2</a>
#     </div>
#     <ul class="nav navbar-nav">
#     <li><a href="Ulike%20typer%20if-setninger.ipynb">Ulike typer if-setninger</a></li>
#     <li><a href="Sammenligning%20av%20strenger.ipynb">Sammenligning av strenger</a></li>
#     <li><a href="Logiske%20operatorer%20og%20logiske%20uttrykk.ipynb">Logiske operatorer og logiske uttrykk</a></li>
#     <li><a href="Forbrytelse%20og%20straff.ipynb">Forbrytelse og straff</a></li>
#     <li class="active"><a href="Aarstider.ipynb">Årstider</a></li>
#         <li ><a href="Tekstbasert%20spill.ipynb">Tekstbasert spill</a></li>
#     <li><a href="Sjakkbrett.ipynb">Sjakkbrett</a></li>
#     <li><a href="Billettpriser%20og%20rabatter.ipynb">Billettpriser og rabatter</a></li>
#     <li><a href="Skatteetaten.ipynb">Skatteetaten</a></li>
#     <li><a href="Epletigging.ipynb">Datamaskinen som tigget epler</a></li>
#     <li><a href="Andregradsligning.ipynb">Andregradsligning</a></li>
#     </ul>
#   </div>
# </nav>
# 
# 
# # Årstider
# 
# **Læringsmål:**
# - Betingelser
# - Logiske uttrykk
# 
# **Starting Out with Python:**
# - Kap. 3.3-3.5
# 
# I denne oppgaven skal en bruker skrive inn dag og måned og få ut hvilken årstid datoen tilhører.
# 
# Et år har (offisielt) fire årstider, og i denne oppgaven tar vi utgangspunkt i at årstidsskiftene følger tabellen nedenfor. **(Merk deg datoene)**
# 
# 
# Årstid | Første dag
# --- | ---
# Vår | 20. mars
# Sommer | 21. juni
# Høst | 22. september
# Vinter | 21. desember

# **Oppgave:** Lag et program som tar inn en måned som en streng og en dag i denne måneden som et tall fra brukeren. Programmet skal så skrive ut årstiden assosiert med denne datoen.
# 
# Du kan anta at inputen er en gyldig dato.
# 
# **Eksempel på kjøring:**
# ```
# Måned: mars
# Dag: 20
# Vår
# ```  
#   
# ```
# Måned: mars
# Dag: 19
# Vinter
# ```  
#   
# ```
# Måned: november
# Dag: 20
# Høst
# ```
# 
# ___Skriv din kode her:___

# In[3]:


måned = input("Måned: ")
dag = int(input("Dag: "))
if måned == ("desember" and dag >= 21) or måned == "januar" or måned == "februar" or (måned == "mars" and dag < 20):
    print("Vinter")

elif måned == ("mars" and dag >= 20) or måned == "apri" or måned == "mai" or (måned == "juni" and dag < 21):
    print("Vår")

elif måned == ("juni" and dag >= 21) or måned == "juli" or måned == "august" or (måned == "september" and dag < 22):
    print("Sommer")

elif måned == ("september" and dag >= 22) or måned == "oktober" or måned == "november" or (måned == "desember" and dag < 21):
    print("Høst")

else:
    print("Vennligst skriv måneden med små bokstaver og dato som et tall")


# In[ ]:




