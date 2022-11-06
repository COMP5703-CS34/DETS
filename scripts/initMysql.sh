#!/bin/bash

mysql --user=detscs341 --password=detscs341 --force --execute="drop database dets;" --force --execute="create database dets; use dets;" --force --execute="source initial.sql;quit;"
