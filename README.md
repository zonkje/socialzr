[![CircleCI](https://circleci.com/gh/zonkje/socialzr/tree/master.svg?style=svg)](https://circleci.com/gh/zonkje/socialzr/tree/master)

# Socialzr Rest API

#### (in development)

[//]: # (ADD ERD DIAGRAM)

The monolithic architecture REST API for performing CRUD operations.
Functionality of the application includes:

+ user registration,
+ writing posts and interacting with them by commenting or adding a thumbs up,
+ creating and joining groups and also writing posts in them,
+ reporting posts and comments with inappropriate content

#### Stack

+ Spring Data JPA
+ Spring Security
+ MapStruct
+ MySQL Database
+ Spring REST Docs
+ Flyway (tba)

## Setup guide

**_NOTE:_**  Make sure you have a docker installed, for example by running.
```bash
docker --version
```

**1. Run MySQL container**

```bash
docker run --name socialzr-db -p 3306:3306 --network=socialzr-net -e MYSQL_ALLOW_EMPTY_PASSWORD=yes -d mysql
```

**2. Configure MySQL**

Execute ***[this script](https://github.com/zonkje/socialzr/blob/master/src/main/scripts/configure-mysql.sql)*** in your mysql app

**3. Run API container**

```bash
docker run --name socialzr-api -p 8080:8080 --network=socialzr-net -e DATABASE_HOST=socialzr-db m0gwai/socialzr
```

All resources are available via url with a prefix http://localhost:8080/

## API Endpoints

Resources: [Post](#postnav), [Post Thumb Up](#postthumbupnav), [Comment](#commentnav)
, [Comment Thumb Up](#commentthumbupnav), [Social Group](#socialgroupnav), [User](#usernav)
, [Contact Information](#contactinformationnav), [Violation Report](#violationreportnav)

#### <a id="postnav">Auth</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| POST | /api/v1/sign_up | Sign up to application | [show sample request body](#signupjson) |
| POST | /api/v1/login | Sign in to application | [show sample request body](#signinjson) |

#### <a id="postnav">Post</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/post/{postId} | Get single post with specific ID |  |
| GET | /api/v1/post | Get most recent posts | optional query params: page and size |
| GET | /api/v1/post/label/id/{labelId} | Get most recent posts by label ID | optional query params: page and size |
| GET | /api/v1/post/label/name/{labelName} | Get most recent posts by label name | optional query params: page and size |
| POST | /api/v1/post | Add new post | [show sample request body](#postjson) |
| PATCH | /api/v1/post/ | Update existing post | [show sample request body](#postjson) |
| DELETE | /api/v1/post/{postId} | Delete post with specific ID |  |

#### <a id="postthumbupnav">Post Thumb Up</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| POST | /api/v1/post/thumb_up | Add thumb up to post | [show sample request body](#postThumbUpjson) |
| DELETE | /api/v1/post/thumb_up/{thumbUpId} | Delete post thumb up |  |

#### <a id="commentnav">Comment</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/comment/{commentId} | Get single comment with specific ID |  |
| GET | /api/v1/comment/my | Get most recent comments by currently logged user | optional query params: page and size |
| GET | /api/v1/comment | Get most recent comments | optional query params: page and size; admin permissions required |
| GET | /api/v1/comment/post/{postId} | Get most recent comments by post ID | optional query params: page and size |
| POST | /api/v1/comment | Add new comment | [show sample request body](#commentjson) |
| PATCH | /api/v1/comment | Update existing comment | [show sample request body](#commentjson) |
| DELETE | /api/v1/comment/{commentId} | Delete comment with specific ID |  |

#### <a id="commentthumbupnav">Comment Thumb Up</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| POST | /api/v1/comment/thumb_up | Add thumb up to comment | [show sample request body](#commentThumbUpjson) |
| DELETE | /api/v1/comment/thumb_up/{thumbUpId} | Delete comment thumb up |  |

#### <a id="socialgroupnav">Social Group</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/social_group/{socialGroupId} | Get single social group with specific ID |  |
| GET | /api/v1/social_group | Get most recent social groups | optional query params: page and size |
| POST | /api/v1/social_group | Add new social group | [show sample request body](#socialgroupjson) |
| PATCH | /api/v1/social_group | Update existing social group | [show sample request body](#socialgroupjson) |
| DELETE | /api/v1/social_group/{socialGroupId} | Delete social group with specific ID | admin permissions required |

#### <a id="usernav">User</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/user/{userId} | Get single user with specific ID |  |
| GET | /api/v1/user | Get most recent users | optional query params: page and size |
| GET | /api/v1/user/socialGroup/{socialGroupId} | Get social group members | optional query params: page and size |
| PATCH | /api/v1/user/ | Update existing user | [show sample request body](#userjson) |
| PATCH | /api/v1/user/join_group/{socialGroupId} | Add user to social group |  |
| PATCH | /api/v1/user/leave_group/{socialGroupId} | Remove user from social group |  |
| DELETE | /api/v1/user/{userId} | Delete user with specific ID; admin permissions required |  |

#### <a id="contactinformationnav">Contact Information</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/contact_information/{contactInformationId} | Get single contact information with specific ID |  |
| POST | /api/v1/contact_information | Add new contact information | [show sample request body](#contactinformationjson) |
| PATCH | /api/v1/contact_information/{contactInformationId} | Update existing contact information with specific ID | [show sample request body](#contactinformationjson) |

#### <a id="violationreportnav">Violation Report</a>

| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/report | Get most recent violation reports | admin permissions required |
| POST | /api/v1/report | Add new violation report | [show sample request body](#violationreportjson) |

## Request body samples

+ <a id="postjson">Post JSON<a/>

```json
{
  "text": "I just write test post",
  "post": [
    "TEST",
    "AD"
  ]
}
```

+ <a id="postThumbUpjson">Post Thumb Up JSON<a/>

```json
{
  "postId": 1
}
```

+ <a id="commentjson">Comment JSON<a/>

```json
{
  "text": "I just write test comment to post with ID 1",
  "postId": 1
}
```

+ <a id="commentThumbUpjson">Comment Thumb Up JSON<a/>

```json
{
  "commentId": 1
}
```

+ <a id="socialgroupjson">Social Group JSON<a/>

```json
{
  "name": "Dunder Mifflin",
  "description": "Sometimes I’ll start a sentence and I don’t even know where it’s going",
  "accessLevel": "PUBLIC"
}
```

+ <a id="signupjson">Sign Up JSON<a/>

```json
{
  "username": "username1",
  "password": "asdasdasd1B",
  "firstName": "Sum Ting",
  "lastName": "Wong",
  "avatarUrl": "https://tinyurl.com/yampgmye"
}
```

+ <a id="signinjson">Sign In JSON<a/>

```json
{
  "username": "username1",
  "password": "asdasdasd1B"
}
```

+ <a id="userjson">User (update) JSON<a/>

```json
{
  "firstName": "Sum Ting",
  "lastName": "Wong",
  "avatarUrl": "https://tinyurl.com/y9ypadkw"
}
```

+ <a id="contactinformationjson">Contact Information JSON</a>

```json
{
  "email": "sumtingwong@email.com",
  "phoneNumber": "555666777",
  "linkedinUrl": "linkedin.com/company/github"
  "address": {
    "address": "88 Colin P Kelly Junior Street",
    "city": "San Francisco",
    "state": "CA 94107",
    "country": "USA"
  }
}
```

+ <a id="violationreportjson">Violation Report JSON<a/>

```json
{
  "text": "Inappropriate profile content",
  "reportedUserId": 2
}
```

Detailed information on endpoints and how to use them are available ***[here](#LINK TO REST DOCS OUTPUT)***