import base64
import email
from unicodedata import category
from django import http
from django.forms import JSONField
from django.http import Http404, HttpResponse, HttpResponseBadRequest, HttpResponseNotFound, JsonResponse, request
from django.shortcuts import get_object_or_404
from .models import CreatePost, CreateUser, Recipe, UpdateProfile, User, GetUserFromID
from .serializer import userSerializer, recipeForm, getRecipeSerializer
import random
import json
from django.contrib.auth import authenticate

def createUser(request):
    if request.method=='POST':
        a = json.loads(request.readline().decode('utf-8'))
        user = CreateUser(a.get('username'),a.get('email'),a.get('password'), a.get('first_name'), a.get('last_name'))
        if user == 0:
            return HttpResponse('fail')
        user_id = authenticate(username = a.get('username'), password = a.get('password'))
        return HttpResponse(user_id.pk)

def randomUser(request):
    return HttpResponse("success")

def loginUser(request):
    if request.method=='POST':
        a = json.loads(request.readline().decode('utf-8'))
        user = authenticate(username=a.get('username'), password=a.get('password'))
        if user is not None:
            return HttpResponse(user.pk)
        else:
            ##return HttpResponseNotFound()
            return HttpResponse("fail")


def getUser(request, user_id):
    if request.method == 'GET':
        try:
            user = get_object_or_404(User, pk=user_id)
            if user is not None:
                ##serialized = userSerializer(user)
                data_details = {'first_name' : user.first_name, 'last_name':user.last_name, 'email' : user.email, 'username' : user.username, 'is_superuser' : user.is_superuser}
                return HttpResponse(json.dumps(data_details))
                # JsonResponse(data_details)
        except:
            user = None
            return HttpResponse(user)

def getuserfromid(request, key):
    if request.method == 'GET':
        return JsonResponse(GetUserFromID(key))


def updateProfile(request):
    if request.method=='POST':
        print("hello")
        a = json.loads(request.readline().decode('utf-8'))
        user = UpdateProfile(a.get('userid'), a.get('username'),a.get('email'),a.get('password'), a.get('first_name'), a.get('last_name'))

def deleteUser(request):
    if request.method == 'POST':
        a = json.loads(request.readline().decode('utf-8'))
        get_object_or_404(User, pk=a.get('userID')).delete()
        return HttpResponse(1)

def deleteRecipe(request):
    if request.method == 'POST':
        a = json.loads(request.readline().decode('utf-8'))
        get_object_or_404(Recipe, pk=a.get('recipeID')).delete()
        return HttpResponse(1)

def handle_uploaded_file(f):
    with open('some/file/name.txt', 'wb+') as destination:
        for chunk in f.chunks():
            destination.write(chunk)

def postRecipe(request):
    if request.method == 'POST':
        try:
            form = recipeForm(request.POST or None, request.FILES or None)
            form.save()
            return HttpResponse('success')
        except:
            return HttpResponseBadRequest('The required fields are not filled')
    else:
        return HttpResponseBadRequest('Bad Request')

def recipe(request, pk):
    if(request.method=='GET'):
        try:
            recipes = Recipe.objects.get(pk = pk)
            s = getRecipeSerializer(recipes)
            return JsonResponse(s.data)
        except:
            return HttpResponseNotFound('notFound')
    else:
        return HttpResponseBadRequest('bad')
        
def getUserRecipes(request, pk):
    if(request.method=='GET'):
            recipes = Recipe.objects.filter(owner_id = pk).all()
            l = {}
            i = 0
            for recipe in recipes:
                l[i] = (getRecipeSerializer(recipe).data)
                l[i]['username'] = get_object_or_404(User, pk=l[i].get('owner_id')).username
                i += 1
            return JsonResponse(l)
    else:
        return HttpResponseBadRequest('bad')
        
def getAll(request):
     if request.method == 'GET':
        try:
            recipes = Recipe.objects.all()
            full_data_details = {}
            i=0
            for recipe in recipes:
                full_data_details[i] = (getRecipeSerializer(recipe).data)
                full_data_details[i]['username'] = get_object_or_404(User, pk=full_data_details[i].get('owner_id')).username
                i+=1
                ## fix image later
                # ownerJSON = getUser(recipe.owner)
                # print(recipe.image)
                # data_details = {'recipe_id' : recipe.pk, 'title' : recipe.title, 'first_name' : ownerJSON['first_name'], 'last_name' : ownerJSON['first_name'], 'image' : recipe.image}
                # full_data_details.append(json.dumps(data_details))
            return JsonResponse(full_data_details)
        except:
            return HttpResponse("failed")

def makeSearch(request, q):
    if request.method == 'GET':
        try:
            recipes = Recipe.objects.filter(title__contains=q).all()
            print("before")
            print(recipes)
            print("after")
            full_data_details = {}
            i=0
            for recipe in recipes:
                full_data_details[i] = (getRecipeSerializer(recipe).data)
                full_data_details[i]['username'] = get_object_or_404(User, pk=full_data_details[i].get('owner_id')).username
                i+=1
                ## fix image later
                # ownerJSON = getUser(recipe.owner)
                # print(recipe.image)
                # data_details = {'recipe_id' : recipe.pk, 'title' : recipe.title, 'first_name' : ownerJSON['first_name'], 'last_name' : ownerJSON['first_name'], 'image' : recipe.image}
                # full_data_details.append(json.dumps(data_details))
            return JsonResponse(full_data_details)
        except:
            return HttpResponse("failed")

def getRecipesFromCategory(request, c):
    if (request.method == 'GET'):
        try:
            #recipes = Recipe.get_queryset(q)
            recipes = Recipe.objects.filter(category__contains=c).all()
            print(recipes)
            l = {}
            i=0
            for recipe in recipes:
                l[i] = (getRecipeSerializer(recipe).data)
                l[i]['username'] = get_object_or_404(User, pk=l[i].get('owner_id')).username
                i+=1
            return JsonResponse(l)
        except:
            return HttpResponse("failed")

