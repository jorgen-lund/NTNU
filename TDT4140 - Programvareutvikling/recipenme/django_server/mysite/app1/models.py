from django.db import connection, models
from django.contrib.auth.models import User
from django.contrib.auth import authenticate
from pyexpat import model
from statistics import mode
import random
from django.http import HttpResponse
from django.shortcuts import get_object_or_404

class Follower(models.Model):
	userThatFollows = models.ForeignKey(User, on_delete = models.CASCADE, related_name="userThatFollows")
	userGetsFollowed = models.ForeignKey(User, on_delete = models.CASCADE, related_name="userGetsFollowed")

class Category(models.Model):
    name = models.CharField(max_length = 50)


class Recipe(models.Model):
    owner_id = models.ForeignKey(User, on_delete = models.CASCADE, related_name="userThatOwns")
    title = models.CharField(max_length = 100)
    image = models.ImageField(upload_to='posted_images/', blank = True, null = True)
    time_estimate = models.CharField(max_length = 50, blank=True)
    preparation = models.TextField(null=True, blank = True)
    ingredients = models.TextField(blank = True, null = True) #Split the ingredients on ","
    scoreOfRecipe = models.FloatField(default=None, null=True, blank=True)
    category = models.TextField(blank = True) #Split the categories on ","
class Comment(models.Model):
    content = models.TextField()
    belongTo = models.ForeignKey(Recipe, on_delete = models.CASCADE, related_name = 'commentedRecipe')

class Like(models.Model):
    userThatLikes = models.ForeignKey(User, on_delete = models.CASCADE)
    recipeThatIsLiked = models.ForeignKey(Recipe, on_delete = models.CASCADE)

# class Score(models.Model):
#     class ScoreValue(models.IntegerChoices):
#         ONE = 1
#         TWO = 2
#         THREE = 3
#         FOUR = 4
#         FIVE = 5
#     #scorid = models.IntegerField(unique=True, nullable=False, primary_key=True)
#     value = models.IntegerField(choices = ScoreValue.choices)
#     scoreFrom = models.ForeignKey(User, on_delete = models.CASCADE)
#     scoreTo = models.ForeignKey(Recipe, on_delete = models.CASCADE)

class Favorite(models.Model):
    #favoritid = models.IntegerField(unique=True, nullable=False, primary_key=True)
    userThatFavorites = models.ForeignKey(User, on_delete = models.CASCADE)
    recipeThatIsFavorited = models.ForeignKey(Recipe, on_delete = models.CASCADE)




#admin.site.register(User, UserAdmin)

def CreateUser(username, email, password, first_name, last_name):
    try:
        obj = get_object_or_404(User, username = username)
        return 0
    except:
        user = User.objects.create_user(username, email, password)
        user.first_name = first_name
        user.last_name = last_name
        user.save()
        return 1
    
def Login(brukernavn, passord):
    user = authenticate(username= brukernavn, password= passord)
    if user is not None:
        return user
    else:
        print("rip")

def GetPosts_User(username):
    liste =[]
    for i in Recipe:
        if (Recipe.get_brukerid().get_username() == username):
            liste += [Recipe.get_self()]
    return liste
        
def CreatePost(user, title, bilde, tid, kategori, tilbredning, ingredienser):
    post = Recipe(owner_id = user, title = title, image= bilde, time_estimate = tid, ingredients = ingredienser, preparation = tilbredning, category = kategori)
    post.save()
    return post

def UpdateProfile(userid, username1, email1, password1, first_name1, last_name1):
    print(userid)
    user= get_object_or_404(User, pk=userid)
    #user = User.objects.get(pk=userid)
    user.username = username1
    print("denne vil vi at ikke skal være null" + user.username)
    print("denne vil vi også at ikke skal være null" + username1)

    user.email = email1
    user.password = password1
    user.first_name = first_name1
    user.last_name = last_name1
    user.save()

def GetUserFromID(userid):
    user= get_object_or_404(User, pk=userid)
    b = user.get_full_name.split(" ")
    a = [user.get_username(), "skriv epost", "skriv passord", b[0], b[1]]
    return a
    