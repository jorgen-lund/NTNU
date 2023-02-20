from django import forms
from rest_framework import serializers
from .models import User, Recipe
class userSerializer(serializers.ModelSerializer):
    class Meta:
        model = User
        fields = ('first_name','last_name','email','password', 'username')
class recipeForm(forms.ModelForm):
    class Meta:
        model = Recipe
        fields = ['owner_id','title','image','time_estimate','preparation','ingredients','scoreOfRecipe','category']
class getRecipeSerializer(serializers.ModelSerializer):
    class Meta:
        model = Recipe
        fields = '__all__'