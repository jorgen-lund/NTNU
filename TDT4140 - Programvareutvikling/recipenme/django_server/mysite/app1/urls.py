#this is the file where we'll add our patterns as we build the application

from django.urls import path
from django.conf.urls.static import static
from django.conf import settings
from . import views
from .models import User

urlpatterns = [
    path('user', views.loginUser, name = 'loginUser'),
    path('user/<int:user_id>', views.getUser, name = 'getSpesificUser'),
    path('random', views.randomUser, name = 'randomUser'),
    path('createUser', views.createUser, name = 'creatingAUser'),
    path('createUser/<int:pk>', views.createUser),
    path('postRecipe', views.postRecipe),
    path('recipe/<int:pk>', views.recipe, name = 'getRecipe'),
    path('updateProfile', views.updateProfile, name = 'updateProfile'),
    path('getUserRecipes/<int:pk>', views.getUserRecipes, name = 'getUserRecipes'),
    path('deleteUser', views.deleteUser, name='deleteUser'),
    path('search/<str:q>', views.makeSearch),
    path('getAll', views.getAll),
    path('filter/<str:c>', views.getRecipesFromCategory),
    path('deleteRecipe', views.deleteRecipe, name = 'deleteRecipe'),
] 
if settings.DEBUG:
    urlpatterns += static(settings.MEDIA_URL, document_root = settings.MEDIA_ROOT)