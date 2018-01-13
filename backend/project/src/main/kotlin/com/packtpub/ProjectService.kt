package com.packtpub

import org.springframework.beans.factory.annotation.Value
import org.springframework.web.reactive.function.client.WebClient

interface ProjectService {
    fun saveProject(project: Project): Project
    fun fetchProjects(): List<Project>
    fun fetchProject(id: Long): Project?
    fun findByOwner(owner: String): List<Project>
    fun fetchAllOwners(): List<String>
    fun fetchProjectsForView(): List<ProjectView>
}

internal class ProjectServiceImpl
(private val projectRepository: ProjectRepository) : ProjectService {

    @Value("\${api.endpoint.url}")
    lateinit var endpoint: String

    override fun fetchProjects(): List<Project> =
        projectRepository.findAll().toList()

    override fun saveProject(project: Project): Project {
        return when (project.id) {
            null -> projectRepository.save(project)
            else -> projectRepository.findById(project.id)
                .map { persistedProject ->
                    projectRepository.save(
                        persistedProject.copy(
                            name = project.name,
                            owner = project.owner,
                            url = project.url,
                            language = project.language
                        ))
                }.orElse(projectRepository.save(project))
        }
    }

    override fun fetchProject(id: Long): Project? =
        projectRepository.findById(id).orElse(null)

    override fun findByOwner(owner: String): List<Project> =
        projectRepository.findByOwner(owner)

    override fun fetchAllOwners(): List<String> =
        projectRepository.retrieveAllOwners()

    override fun fetchProjectsForView(): List<ProjectView> =
        projectRepository.retrieveAllProjectsForView()

    private fun fetchProjects(project: Project) {
        val webclient = WebClient.create(endpoint)
    }
}