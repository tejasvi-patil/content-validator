# content-validator

This is a simple application to moderate/validate the comment text to prevent customers from posting
objectionable content.

API accepts the user comment as json 
```json
{
	"userComment":"User comment comes here"
}
```

And responds accordingly after validating its content.

```json
{
    "containsObjectionableContent": false,
    "message": "No objectionable content found"
}
```

If any objectionable content is found the response would give feedback accordingly
```json
{
    "containsObjectionableContent": true,
    "message": "Comments/Feedback contains objectionable content :[Violent, Adult]"
}
```
