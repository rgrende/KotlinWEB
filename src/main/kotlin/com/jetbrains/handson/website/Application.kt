//Kotlin Web Project
//This code was cloned from GitHub and here is the link: https://github.com/ktorio/ktor-website-sample
//This is the bare minimum of what this project was asking. I struggled to understand how
//Kotr works both on the server and the client side. I tried to make a four-page website using
//html and css but didn't know (nor could I find any helpful tips) on how to connect them.
//I wrote about 40% of this code and fully expect a failing grade for not the lack of content.
//I have no idea what parts of this project are useful and which parts are not.

//imports for ktor and kotlin
package com.jetbrains.handson.website

import freemarker.cache.*
import freemarker.core.HTMLOutputFormat
import io.ktor.application.*
import io.ktor.freemarker.*
import io.ktor.http.HttpStatusCode
import io.ktor.http.content.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.html.*
import io.ktor.features.*
import io.ktor.html.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

//main function that uses an embedded server "netty"
fun main() {
    embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondHtmlTemplate(LayoutTemplate()) {
                    header {
                        +"Ktor"
                    }
                    content {
                        articleTitle {
                            +"Hello from Ktor!"
                        }
                        articleText {
                            +"Kotlin Framework for creating connected systems."
                        }
                    }
                }
            }
        }
    }.start(wait = true)
}

//function that is responsible for running the application
fun Application.module() {
    install(FreeMarker) {
        templateLoader = ClassTemplateLoader(this::class.java.classLoader, "templates")
        outputFormat = HTMLOutputFormat.INSTANCE

    }
}
//class that designs the template for HTML (Parent)
class LayoutTemplate: Template<HTML> {
    val header = Placeholder<FlowContent>()
    val content = TemplatePlaceholder<ContentTemplate>()
    override fun HTML.apply() {
        body {
            h1 {
                insert(header)
            }
            insert(ContentTemplate(), content)
        }
    }
}

//class that designs the template for HTML (Child)
class ContentTemplate: Template<FlowContent> {
    val articleTitle = Placeholder<FlowContent>()
    val articleText = Placeholder<FlowContent>()
    override fun FlowContent.apply() {
        article {
            h2 {
                insert(articleTitle)
            }
            p {
                insert(articleText)
            }
        }
    }
}
