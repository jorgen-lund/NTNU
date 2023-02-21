#!/usr/bin/env python
# coding: utf-8

# <nav class="navbar navbar-default">
#   <div class="container-fluid">
#     <div class="navbar-header">
#       <a class="navbar-brand" href="_Oving7.ipynb">Øving 7</a>
#     </div>
#     <ul class="nav navbar-nav">
#     <li ><a href="Aksessering.ipynb">Aksessering av karakter</a></li>
#     <li ><a href="Strenger%20og%20konkatinering.ipynb">Konkatinering</a></li>
#     <li ><a href="Slicing.ipynb">Slicing</a></li>
#     <li ><a href="Tekstbehandling.ipynb">Tekstbehandling</a></li>
#     <li class="active"><a href="Strenghandtering.ipynb">Strenghåndtering</a></li>
#     <li ><a href="Innebygde%20funksjoner.ipynb">Innebygde funksjoner og lister</a></li>
#     <li><a href="Fjesboka.ipynb">Fjesboka</a></li>
#     <li ><a href="Akkorder%20og%20toner.ipynb">Akkorder og toner</a></li>
#     <li ><a href="Ideel%20gasslov.ipynb">Ideel Gasslov</a></li>
#     <li><a href="Sammenhengende%20tallrekke.ipynb">Sammenhengende Tallrekke</a></li>
#     <li ><a href="Sortering.ipynb">Sortering</a></li>
#     <li ><a href="Strengmanipulasjon.ipynb">Strengmanipulasjon</a></li>
#     <li ><a href="Kryptering.ipynb">Kryptering</a></li>
#     <li ><a href="Litt%20sjakk.ipynb">Litt Sjakk</a></li>
#     </ul>
#   </div>
# </nav>
# 
# # Strenghåndtering
# 
# **Læringsmål:**
# 
# * Strenger
# * Løkker
# 
# **Starting Out with Python:**
# 
# * Kap. 8: More About Strings
#  
# 
# I denne oppgaven skal vi lære å sammenligne, reversere og sjekke om en streng er et palindrom.

# ### a)

# Lag en funksjon `check_equal(str1, str2)` som sjekker om to strenger er like ved å sammenligne dem tegn for tegn. Funksjonen returnerer `True` hvis strengene er like; `False` ellers.
# 
# ***Skriv koden din i kodeblokken under:***

# In[21]:


def check_equal(str1, str2):
    for i in range(len(str1)):
        if str1[i] != str2[i]:
            return False
    return True

    
print(check_equal("Hei Alle SAMMEN", "HeI Alle SAMMEN"))


# **Test av kode:**

# In[18]:


str1 = 'hei'
str2 = 'hello'
str3 = 'hello'
print(check_equal(str1, str2))
print(check_equal(str3, str2))
# Denne koden skal returnere:
# False
# True


# ### b)

# Lag en funksjon `reversed_word(string)` som tar inn en streng, reverserer den og returnerer den reverserte strengen. Dette skal gjøres tegn for tegn med en løkke.
# 
# ***Skriv koden din i kodeblokken under:***

# In[32]:


def reversed_word(string):
    s1 = ""
    for i in string:
        s1 = i + s1
    return s1

print(reversed_word("gad etsene revh gaddim lit gnillyk resips geJ"))


# **Test av kode:**

# In[24]:


string = 'star desserts'
print(reversed_word(string))
# Denne koden skal returnere:
# stressed rats


# ### c)

# Et palindrom er et ord som staves likt begge veier (eks. “abba”). Lag en funksjon `check_palindrome(string)` som returnerer `True` om en streng er et palindrom; `False` ellers.
# 
# ***Skriv koden din i kodeblokken under:***

# In[33]:


def check_palindrome(string):
    s1 = ""
    for i in string:
        s1 = i + s1
    if s1 != string:
        return False
    return True

    
        
    


# **Test av kode:**

# In[36]:


str1 = 'agnes i senga'
str2 = 'hello'
print(check_palindrome(str1))
print(check_palindrome(str2))
# Denne koden skal returnere:
# True
# False


# #### Hint

# bruk metoder du har laget tidligere som hjelpemetoder.

# ### d)

# Lag en funksjon `contains_string(str1, str2)` som tar inn to strenger og sjekker om den første strengen inneholder den andre. Dersom den gjør det, returner **posisjonen** den andre strengen begynner på (fra 0). Returner **-1** ellers.
# 
# ***Skriv koden din i kodeblokken under:***

# In[ ]:


def contains_str:ing(str1, str2):
    if str1 in str2
        return 


# **Test av kode:**

# In[ ]:


str1 = 'pepperkake'
str2 = 'per'
str3 = 'ola'
print(contains_string(str1, str2))
print(contains_string(str1, str3))
# Denne koden skal returnere:
# 3
# -1

