[![CircleCI](https://circleci.com/gh/zonkje/socialzr/tree/master.svg?style=svg)](https://circleci.com/gh/zonkje/socialzr/tree/master)

# Socialzr Rest API

[//]: # (ADD ERD DIAGRAM)

[general info description here]
Functionality of the application inclused:
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
+ Flyway

## Setup guide
**1. Run MySQL container**
```bash
docker run [command here]
```
**2. Step 2**
[TBA]

## API Endpoints
Models: [Post](#postnav), [Comment](#commentnav), [Social Group](#socialgroupnav), [User](#usernav), [Contact Information](#contactinformationnav)

#### <a id="postnav">Post</a>
| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/post | Get most recent posts | optional query params: page and size |
| GET | /api/v1/post/{postId} | Get single post with specific ID |  |
| POST | /api/v1/post | Create new post | [show sample request body](#postjson) |
| PATCH | /api/v1/post/{postId} | Update existing post with specific ID | [show sample request body](#postjson) |
| DELETE | /api/v1/post/{postId} | Delete post with specific ID |  |
| GET | /api/v1/post/label/{labelId} | Get most recent posts by label with specific ID |  |

#### <a id="commentnav">Comment</a>
| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/comment | Get most recent comments | optional query params: page and size |
| GET | /api/v1/comment/{commentId} | Get single comment with specific ID |  |
| POST | /api/v1/comment | Create new comment | [show sample request body](#commentjson) |
| PATCH | /api/v1/comment/{commentId} | Update existing comment with specific ID | [show sample request body](#commentjson) |
| DELETE | /api/v1/comment/{commentId} | Delete comment with specific ID |  |
#### <a id="socialgroupnav">Social Group</a>
| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/social_group | Get most recent social groups | optional query params: page and size |
| GET | /api/v1/social_group/{socialGroupId} | Get single social group with specific ID |  |
| POST | /api/v1/social_group | Create new social group | [show sample request body](#socialgroupjson) |
| PATCH | /api/v1/social_group/{socialGroupId} | Update existing social group with specific ID | [show sample request body](#socialgroupjson) |
| DELETE | /api/v1/social_group/{socialGroupId} | Delete social group with specific ID |  |
| GET | /api/v1/social_group/members/{socialGroupId} | Get most recent members of social group with specific ID | optional query params: page and size |
#### <a id="usernav">User</a>
| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/user | Get most recent users | optional query params: page and size |
| GET | /api/v1/user/{userId} | Get single user with specific ID |  |
| POST | /api/v1/user | Create new user | [show sample request body](#userjson) |
| PATCH | /api/v1/user/{userId} | Update existing user with specific ID | [show sample request body](#userjson) |
| DELETE | /api/v1/user/{userId} | Delete user with specific ID |  |
| PATCH | /api/v1/user/{userId}/join_group/{socialGroupId} | Add user to social group |  |
| PATCH | /api/v1/user/{userId}/leave_group/{socialGroupId} | Remove user from social group |  |
#### <a id="contactinformationnav">Contact Information</a>
| HTTP Method | URL | Description | Additional Info |
| --- | --- | --- | --- |
| GET | /api/v1/contact_information/{contactInformationId} | Get single contact information with specific ID |  |
| POST | /api/v1/contact_information | Create new contact information | [show sample request body](#contactinformationjson) |
| PATCH | /api/v1/contact_information/{contactInformationId} | Update existing contact information with specific ID | [show sample request body](#contactinformationjson) |
## Request body samples
+ <a id="postjson">Post JSON<a/>
```json
{
    "text": "I just write test post",
    "authorId": 1
}
```
+ <a id="commentjson">Comment JSON<a/>
```json
{
    "text": "I just write test comment to post with ID 1",
    "authorId": 1,
    "postId": 1
}
```
+ <a id="socialgroupjson">Social Group JSON<a/>
```json
{
    "name": "Dunder Mifflin",
    "description": "Sometimes I’ll start a sentence and I don’t even know where it’s going",
    "creatorId": 1,
    "accessLevel": "PUBLIC"
}
```
+ <a id="userjson">User JSON<a/>
```json
{
    "firstName": "Sum Ting",
    "lastName": "Wong"
}
```
+ <a id="contactinformationjson">Contact Information JSON</a>
```json
{
    "email": "sumtingwong@email.com",
    "phoneNumber": "555666777",
    "linkedinUrl": "linkedin.com/company/github"
    "address":
        {
            "address": "88 Colin P Kelly Junior Street",
            "city": "San Francisco",
            "state": "CA 94107",
            "country": "USA",
        }
}
```
Detailed information on endpoints and how to use them are available ***[here](#LINK TO REST DOCS OUTPUT)***