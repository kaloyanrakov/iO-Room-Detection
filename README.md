<h1 align="center">iO Digital Room Detection</h1>

<p align="center">
  <img src="Documentation/Images/fontys-logo.png"width="100">
  <img src="Documentation/Images/iO-logo-300x300-blue.jpg"width="100">
</p>


<p align="center">
A group project for tracking meeting room occupancy with cameras and AI-based person detection, displayed on a live dashboard. Designed for iO Digital (https://www.iodigital.com) through Fontys ICT.
</p>

<h2 align="center">Assignment</h2>
iO Digital is facing inconsistencies in their meeting room booking system, with rooms appearing as “in use” even when unoccupied. The goal of the project is to build a monitoring system that counts the number of people in a room. The data is to be processed locally and sent to a web dashboard that displays each room, its current occupancy, and its status. The system is to be scalable and capable of being implemented across multiple iO locations.

<h2 align="center">Our Solution</h2>
Firstly, using cameras, we scan the room for people while protecting their identities for privacy reasons:

<p align="center" style="margin: 20px 0;">
  <img src="Documentation/Images/Screenshot 2025-07-03 165212.png"width="500">
</p>
Afterwards we log the number of people in each room and in case the room is empty 15 minutes after the meeting was supposed to start, we send a warning message. If a user logs in, they can add new rooms and cameras. This ensures that the system can be easily implemented in multiple locations.

<p align="center" style="margin: 20px 0;">
  <img src="Documentation/Images/Screenshot_2024-12-17_101303.png"width="500">
</p>


<h2 align="center">Technologies used:</h2>

<div align="center" style="display: flex; flex-wrap: wrap; justify-content: center; gap: 8px;">
  <a href="#"><img src="https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=docker&logoColor=white" alt="Docker" /></a>
  <a href="#"><img src="https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white" alt="MySQL" /></a>
  <a href="#"><img src="https://img.shields.io/badge/Java-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java" /></a>
  <a href="#"><img src="https://img.shields.io/badge/Vite-646CFF?style=for-the-badge&logo=vite&logoColor=white" alt="Vite" /></a>
  <a href="#"><img src="https://img.shields.io/badge/React-61DAFB?style=for-the-badge&logo=react&logoColor=black" alt="React" /></a>
  <a href="#"><img src="https://img.shields.io/badge/Raspberry_Pi-C51A4A?style=for-the-badge&logo=raspberry-pi&logoColor=white" alt="Raspberry Pi" /></a>
  <a href="#"><img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=javascript&logoColor=black" alt="JavaScript" /></a>
  <a href="#"><img src="https://img.shields.io/badge/Spring_Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white" alt="Spring Boot" /></a>

  <a href="#"><img src="https://img.shields.io/badge/HTML-E34F26?style=for-the-badge&logo=html5&logoColor=white" alt="HTML" /></a>
  <a href="#"><img src="https://img.shields.io/badge/CSS-1572B6?style=for-the-badge&logo=css3&logoColor=white" alt="CSS" /></a>
  <a href="#"><img src="https://img.shields.io/badge/OpenCV-5C3EE8?style=for-the-badge&logo=opencv&logoColor=white" alt="OpenCV" /></a>

</div>


<h2 align="center">Clone the repository:</h2>

```bash
git clone https://github.com/kaloyanrakov/iO-Room-Detection.git
cd iO-Room-Detection