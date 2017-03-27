from flask import g, abort,Flask, render_template, request, session, url_for, redirect
from functools import wraps
import pymysql
import datetime
import os
import base64

app = Flask(__name__)

#Temp secret key, make random, basically session key
app.secret_key = os.urandom(32)

#conn = pymysql.connect(host = 'localhost',
#                       user= 'root',
#                       password = 'root',
#                       db = 'findfolks',
#                       charset = 'utf8mb4',
#                       cursorclass = pymysql.cursors.DictCursor)

@app.route("/")
def home():
	return "Hello World!";
	
if __name__ == "__main__":
    app.run('127.0.0.1', 5000, debug = True)
