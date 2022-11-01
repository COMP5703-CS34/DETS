#!/bin/bash

mysql --user=user --password=123456 --force --execute="drop database dets;" --force --execute="create database dets; use dets;" --force --execute="drop table request;" --force --execute="source initial.sql;quit;"
